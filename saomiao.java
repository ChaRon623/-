package kuangyaowen;


import java.io.*;

public class saomiao {
    static String[] keyword ={
            "include","using","namespace","std","main","endl","iostream",
            "char","double","enum","float","int","long","short",
            "signed","struct","union","unsigned","void","for","do",
            "while","break","continue","if","else","goto", "switch",
            "case","default","return", "auto","cout","register","static",
            "const","sizeof","typedof","volatile","bool","true","false",
            "class","public","private","protected","friend","mutable","explicit",
            "this","virtual", "new","delete","try","catch","throw"
    };
    static String[] operator ={
            "[","]","(",")","%","~","^",",",".","=","==","+","++","+=","-","--","->",
            "-=","*","*=","/","/=","<","<<","<=",">",">=",">>","&","&&","|","||","!",
            "!="
    };
    enum TYPE { KEYW, ID, NUM, OPER, NOTE, SPEC, STR }

    public saomiao(String path)
    {
        try{
            File filename = new File(path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            String buff=null;
            line = br.readLine();
            buff=line+'\n';
            while (line != null) {
                line = br.readLine();
                if(line!=null)
                    buff=buff+line+'\n'; // 一次读入一行数据
            }

            num=buff.length();
            buf=buff.toCharArray();
            num=num-1;
            buf[num]='\0';



        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(num);
        for (int i=0;i<num;i++)
        {
            assert buf != null;
            System.out.print(buf[i]);
        }
        System.out.println();
    }

    public boolean isDigit(char ch)
    {
        if (ch >= '0' && ch <= '9')
        {
            return true;
        }
        return false;
    }

    public boolean isLetter(char ch)
    {
        if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a'&& ch <= 'z'))
        {
            return true;
        }
        return false;
    }

    public boolean isKeyWord(String s)
    {
        for (int i = 0; i < 56; i++)
        {
            if (s.equals(keyword[i]))
            {
                return true;
            }
        }
        return false;
    }
    public int ret(String s, TYPE type)
    {
        if(type==TYPE.ID)
        {
            for(int i=0;i<num1;i++)
            {
                if(s.equals(id[i]))
                {
                    return i;
                }
            }
        }
        if(type==TYPE.KEYW)
        {
            for(int i=0;i<56;i++)
            {
                if(s.equals(keyword[i]))
                {
                    return i;
                }
            }
        }
        if(type==TYPE.NUM)
        {
            for(int i=0;i<num2;i++)
            {
                if(s.equals(number[i]))
                {
                    return i;
                }
            }
        }
        if(type==TYPE.OPER)
        {
            for(int i=0;i<34;i++)
            {
                if(s.equals(operator[i]))
                {
                    return i+56;
                }
            }
        }
        if(type==TYPE.NOTE)
        {
            if(s.equals("/*"))
            {
                return 90;
            }
            if(s.equals("*/"))
            {
                return 91;
            }
            if(s.equals("//"))
            {
                return 92;
            }
        }
        if(type==TYPE.SPEC)
        {
            for(int i=0;i<num3;i++)
            {
                if(s.equals(spci[i]))
                {
                    return i+93;
                }
            }
        }
        if(type==TYPE.STR)
        {
            for(int i=0;i<num4;i++)
            {
                if(s.equals(str[i]))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public void print(String s, TYPE type)
    {
        switch (type)
        {
            case KEYW:
                System.out.println(s+'\t'+'\t'+'\t'+"("+ret(s,type)+","+'-'+")");
                break;
            case ID:
                System.out.println(s+'\t'+'\t'+'\t'+"("+98+","+ "name["+ret(s,type)+"])");
                break;
            case NUM:
                int f=Integer.parseInt(s);
                System.out.println(s+'\t'+'\t'+'\t'+"("+97+","+Integer.toBinaryString(f)+")");
                break;
            case OPER:
                System.out.println(s+'\t'+'\t'+'\t'+"("+ret(s,type)+","+"-)");
                break;
            case NOTE:
                System.out.println(s+'\t'+'\t'+'\t'+"("+ret(s,type)+","+"-)");
                break;
            case SPEC:
                System.out.println(s+'\t'+'\t'+'\t'+"("+ret(s,type)+","+"-)");

                break;
            case STR:
                System.out.println(s+'\t'+'\t'+'\t'+"("+99+","+ "str["+ret(s,type)+"])");
                break;
            default:
                break;
        }

    }
    boolean judge(String s,String[] p)
    {
        for(int i=0;i<p.length;i++)
        {
            if(s.equals(p[i]))
            {
                return true;
            }
        }
        return false;
    }

    void analyse()
    {
        TYPE type;
        int i = 0;
        num1=num2=num3=num4=0;
        char ch;
        while (i <= num && (ch = buf[i]) != '\0')
        {
            while (ch == ' ' || ch == '\t' || ch == '\n')
            {
                i++;
                ch = buf[i];
            }
            String s = "";
            if (isLetter(ch) || ch == '_')
            {
                while (isLetter(ch) || isDigit(ch) || ch == '_')
                {
                    s = s + ch;
                    i++;
                    ch = buf[i];
                }
                if (isKeyWord(s))
                {
                    type = TYPE.KEYW;
                }
                else
                {
                    type = TYPE.ID;
                    if(!judge(s,id))
                    {
                        id[num1] = s;
                        num1++;
                    }
                }
                print(s, type);
                s = "";
            }
            else
            {
                if (isDigit(ch))
                {
                    while (ch == '.' || isDigit(ch))
                    {
                        s = s + ch;
                        i++;
                        ch = buf[i];
                    }
                    type = TYPE.NUM;
                    float a =Float.parseFloat(s);
                    if(!judge(s,number))
                    {
                        number[num2] = s;
                        num2++;
                    }
                    print(s, type);
                    s = "";
                }
                else
                {
                    type = TYPE.OPER;
                    switch (ch)
                    {
                        case '[':
                        case ']':
                        case '(':
                        case ')':
                        case '%':
                        case '~':
                        case '^':
                        case ',':
                        case '.':
                        {
                            s = s + ch;
                            i++;
                            break;
                        }
                        case '=':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '=')
                            {
                                s = s + ch;
                                i++;
                            }
                            break;
                        }
                        case '+':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '+' || ch == '=')
                            {
                                s = s + ch;
                                i++;
                            }
                            break;
                        }
                        case '-':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '-' || ch == '>' || ch == '=')
                            {
                                s = s + ch;
                                i++;
                            }
                            break;
                        }
                        case '*':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '/')
                            {
                                s = s + ch;
                                type = TYPE.NOTE;
                                i++;
                            }
                            else
                            {
                                if (ch == '=')
                                {
                                    s = s + ch;
                                    i++;
                                }
                            }
                            break;
                        }
                        case '/':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            switch (ch)
                            {
                                case '/':
                                {
                                    s = s + ch;
                                    type = TYPE.NOTE;
                                    i++;
                                    while ((ch = buf[i]) != '\n')
                                    {
                                        i++;
                                    }
                                    break;
                                }

                                case '*':
                                {
                                    s = s + ch;
                                    type = TYPE.NOTE;
                                    i++;
                                    while ((ch = buf[i]) != '*' && buf[i + 1] != '/')
                                    {
                                        i++;
                                    }
                                    break;

                                }

                                case '=':
                                {
                                    s = s + ch;
                                    i++;
                                    break;
                                }

                                default:
                                    break;
                            }
                            break;
                        }
                        case '<':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            switch (ch)
                            {
                                case '<':
                                case '=':
                                {
                                    s = s + ch;
                                    i++;
                                    break;
                                }
                                default:
                                    break;
                            }
                            break;
                        }
                        case '>':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            switch (ch)
                            {
                                case '>':
                                case '=':
                                {
                                    s = s + ch;
                                    i++;
                                    break;
                                }
                                default:
                                    break;
                            }
                            break;
                        }
                        case '&':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '&')
                            {
                                s = s + ch;
                                i++;
                            }
                            break;
                        }
                        case '|':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '|')
                            {
                                s = s + ch;
                                i++;
                            }
                            break;
                        }
                        case '!':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            if (ch == '=')
                            {
                                s = s + ch;
                                i++;
                            }
                            break;
                        }
                        case '\'':
                        {
                            s = s + ch;
                            i++;
                            ch = buf[i];
                            while (ch != '\'')
                            {
                                s = s + ch;
                                i++;
                                if (ch == '\\')
                                {
                                    ch = buf[i];	//处理'\''的情况
                                    s = s + ch;
                                    i++;
                                }
                                ch = buf[i];
                            }
                            s = s + ch;
                            type = TYPE.STR;
                            if(!judge(s,str))
                            {
                                str[num4] = s;
                                num4++;
                            }
                            i++;
                            break;
                        }
                        case '\"':
                        {
                            s = s + '"';
                            i++;
                            ch = buf[i];
                            while (ch != '"')
                            {
                                s = s + ch;
                                i++;
                                ch = buf[i];
                            }
                            s = s + '"';
                            type = TYPE.STR;
                            if(!judge(s,str))
                            {
                                str[num4] = s;
                                num4++;
                            }
                            i++;
                            break;
                        }

                        default:
                        {
                            type = TYPE.SPEC;
                            s = s + ch;
                            i++;
                            if(!judge(s,spci))
                            {
                                spci[num3] = s;
                                num3++;
                            }
                            break;
                        }

                    }
                    print(s, type);
                    s = "";
                }
            }
        }
    }

    String getResult()
    {
        return result;
    }

    public char buf[];
    public String[] id = new String[10000];
    public String[] number = new String[10000];
    public String[] spci = new String[10000];
    public String[] str = new String[10000];
    public int num,num1,num2,num3,num4;
    public String result;


    public static void main(String[] args){
        saomiao sm = new saomiao("D:\\practiceWorks\\assignments\\fundamentalsofCompiling\\蓝桥杯\\src\\kuangyaowen\\words.txt");
        sm.analyse();
    }
}
