
package io.github.azige.whitespace.szm;

import static io.github.azige.whitespace.szm.SzmToken.Type.*;

import java.io.IOException;
import io.github.azige.whitespace.WhitespaceException;

%%

%public
%final
%class SzmTokenizerImpl
%implements SzmTokenizer
%type SzmToken
%apiprivate
%unicode
%char

%{
    @Override
    public SzmToken next(){
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

    private SzmToken command(SzmToken.Type type){
        return new SzmToken(type);
    }

    private int number;

    private void numberBegin(){
        yybegin(STATE_NUMBER);
        number = 0;
    }

    private void numberZero(){
        number <<= 1;
    }

    private void numberOne(){
        number <<= 1;
        number |= 1;
    }

    private SzmToken number(){
        yybegin(YYINITIAL);
        return new SzmToken(number);
    }
%}

Shui    = \u6c34
Zi      = \u6c9d
Miao    = \u6dfc

Ignore  = [^\u6c34\u6c9d\u6dfc]

S       = {Ignore}* {Shui}
Z       = {Ignore}* {Zi}
M       = {Ignore}* {Miao}

%xstate STATE_NUMBER

%%

{S} {S} {S} {S}                 { numberBegin();
                                  return command(V_LOAD); }
{S} {S} {S} {Z} 	            { numberBegin();
                                  return command(V_STORE); }
{S} {S} {S} {M} 	            { numberBegin();
                                  return command(V_GLOAD); }
{S} {S} {Z} {S} 	            { numberBegin();
                                  return command(V_GSTORE); }
{S} {S} {Z} {Z} 	            { numberBegin();
                                  return command(S_PUSH); }
{S} {S} {Z} {M}                 { return command(S_DISCARD); }
{S} {S} {M} {S} 	            { return command(S_DUP); }
{S} {S} {M} {Z} 	            { return command(S_SWAP); }
{S} {S} {M} {M} 	            { return command(A_ADD); }
{S} {Z} {S} {S} 	            { return command(A_SUB); }
{S} {Z} {S} {Z} 	            { return command(A_MUL); }
{S} {Z} {S} {M} 	            { return command(A_DIV); }
{S} {Z} {Z} {S} 	            { return command(A_MOD); }
{S} {Z} {Z} {Z} 	            { return command(A_CMP); }
{S} {Z} {Z} {M} 	            { numberBegin();
                                  return command(A_SHL); }
{S} {Z} {M} {S} 	            { numberBegin();
                                  return command(A_SHR); }
{S} {Z} {M} {Z} 	            { numberBegin();
                                  return command(A_USHR); }
{S} {Z} {M} {M} 	            { return command(A_NOT); }
{S} {M} {S} {S} 	            { return command(A_AND); }
{S} {M} {S} {Z} 	            { return command(A_OR); }
{S} {M} {S} {M} 	            { return command(A_XOR); }
{S} {M} {Z} {S} 	            { numberBegin();
                                  return command(F_FUNC); }
{S} {M} {Z} {Z} 	            { numberBegin();
                                  return command(F_MARK); }
{S} {M} {Z} {M} 	            { numberBegin();
                                  return command(F_JUMP); }
{S} {M} {M} {S} 	            { numberBegin();
                                  return command(F_JUMPZ); }
{S} {M} {M} {Z} 	            { numberBegin();
                                  return command(F_JUMPN); }
{S} {M} {M} {M} 	            { numberBegin();
                                  return command(F_JUMPP); }
{Z} {S} {S} {S} 	            { numberBegin();
                                  return command(F_CALL); }
{Z} {S} {S} {Z} 	            { return command(F_RETURN); }
{Z} {S} {S} {M} 	            { numberBegin();
                                  return command(F_EXIT); }
{Z} {S} {Z} {S} 	            { return command(I_PCHAR); }
{Z} {S} {Z} {Z} 	            { return command(I_PNUM); }
{Z} {S} {Z} {M} 	            { return command(I_RCHAR); }
{Z} {S} {M} {S} 	            { return command(I_RNUM); }
{Z} {S} {M} {Z} 	            { return command(R_BREAKPOINT); }
{Z} {S} {M} {M} 	            { return command(R_IMPDEP1); }

<STATE_NUMBER> {
    {S}                         { numberOne(); }
    {Z}                         { numberZero(); }
    {M}                         { return number(); }
}
