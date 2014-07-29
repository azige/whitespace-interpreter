
package io.github.azige.whitespace.text;

import static io.github.azige.whitespace.text.Token.Type.*;

import java.math.BigInteger;
import java.io.IOException;
import io.github.azige.whitespace.WhitespaceException;

%%

%public
%final
%class Tokenizer
%implements AutoCloseable
%type Token
%apiprivate
%unicode
%char

%{
    public Token next(){
        try{
            return yylex();
        } catch (IOException ex){
            throw new WhitespaceException(ex);
        }
    }

    @Override
    public void close() throws IOException{
        zzReader.close();
    }

    private Token command(Token.Type type){
        return new Token(type);
    }

    private StringBuilder labelBuilder;

    private void labelBegin(){
        yybegin(STATE_LABEL);
        labelBuilder = new StringBuilder();
    }

    private void labelSpace(){
        labelBuilder.append('S');
    }

    private void labelTab(){
        labelBuilder.append('T');
    }

    private Token label(){
        yybegin(YYINITIAL);
        String label = labelBuilder.toString();
        labelBuilder = null;
        return new Token(label);
    }

    private StringBuilder numberBuilder;

    private void numberBegin(){
        yybegin(NUMBER_SIGN);
        numberBuilder = new StringBuilder();
    }

    /**
     * Set the sign of the number
     *
     * @param flag true for positive，false for negative
     */
    private void numberSign(boolean flag){
        if (!flag){
            numberBuilder.append('-');
        }
        yybegin(NUMBER_DIGIT);
    }

    private void numberZero(){
        numberBuilder.append('0');
    }

    private void numberOne(){
        numberBuilder.append('1');
    }

    private Token number(){
        yybegin(YYINITIAL);
        BigInteger number = new BigInteger(numberBuilder.toString(), 2);
        numberBuilder = null;
        return new Token(number);
    }
%}

Space   = \u0020
Tab     = \u0009
LF      = \u000a

Ignore  = [^\u0020\u0009\u000a]

S       = {Ignore}* {Space}
T       = {Ignore}* {Tab}
L       = {Ignore}* {LF}

IMP_S   = {S}
IMP_A   = {T} {S}
IMP_H   = {T} {T}
IMP_F   = {L}
IMP_I   = {T} {L}

%xstate STATE_LABEL, NUMBER_SIGN, NUMBER_DIGIT

%%

{IMP_S} {S}                 { numberBegin();
                              return command(S_PUSH); }
{IMP_S} {L} {S}             { return command(S_DUP); }
{IMP_S} {T} {S}             { numberBegin();
                              return command(S_DUP2); }
{IMP_S} {L} {T}             { return command(S_SWAP); }
{IMP_S} {L} {L}             { return command(S_DISCARD); }
{IMP_S} {T} {L}             { numberBegin();
                              return command(S_REMOVE); }

{IMP_A} {S} {S}             { return command(A_ADD); }
{IMP_A} {S} {T}             { return command(A_SUB); }
{IMP_A} {S} {L}             { return command(A_MUL); }
{IMP_A} {T} {S}             { return command(A_DIV); }
{IMP_A} {T} {T}             { return command(A_MOD); }

{IMP_H} {S}                 { return command(H_STORE); }
{IMP_H} {T}                 { return command(H_RETRIEVE); }

{IMP_F} {S} {S}             { labelBegin();
                              return command(F_MARK); }
{IMP_F} {S} {T}             { labelBegin();
                              return command(F_CALL); }
{IMP_F} {S} {L}             { labelBegin();
                              return command(F_JUMP); }
{IMP_F} {T} {S}             { labelBegin();
                              return command(F_JUMPZ); }
{IMP_F} {T} {T}             { labelBegin();
                              return command(F_JUMPN); }
{IMP_F} {T} {L}             { return command(F_RETURN); }
{IMP_F} {L} {L}             { return command(F_EXIT); }

{IMP_I} {S} {S}             { return command(I_PCHAR); }
{IMP_I} {S} {T}             { return command(I_PNUM); }
{IMP_I} {T} {S}             { return command(I_RCHAR); }
{IMP_I} {T} {T}             { return command(I_RNUM); }

<NUMBER_SIGN> {
    {S}                     { numberSign(true); }
    {T}                     { numberSign(false); }
}

<NUMBER_DIGIT> {
    {S}                     { numberZero(); }
    {T}                     { numberOne(); }
    {L}                     { return number(); }
}

<STATE_LABEL> {
    {S}                     { labelSpace(); }
    {T}                     { labelTab(); }
    {L}                     { return label(); }
}
