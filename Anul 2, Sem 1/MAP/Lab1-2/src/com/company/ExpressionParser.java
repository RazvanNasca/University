package com.company;

public class ExpressionParser {

    public ComplexExpression ExpressionParser(String[] args)
    {
        if(Valid(args))
        {
            ExpressionFactory exp=ExpressionFactory.getInstance();
            NumarComplex[] numbers = buildArgs(args);

            if(args[1].equals("+"))
                return exp.createExpression(Operation.ADDITION,numbers);
            if(args[1].equals("-"))
                return exp.createExpression(Operation.SUBTRACTION,numbers);
            if(args[1].equals("*"))
                return exp.createExpression(Operation.MULTIPLICATION,numbers);
            return exp.createExpression(Operation.DIVISION,numbers);
        }

        return null;
    }

    private boolean Valid(String[] args)
    {
        if(args.length % 2 == 0 || args.length == 1)
            return false;

        String[] structura = {
                "-?\\d+(\\.\\d+)?[+-]\\d+(\\.\\d+)?\\*i", /// 2+3*i
                "-?\\d+(\\.\\d+)?",                       /// 2
                "-?\\d+(\\.\\d+)?\\*i",                   /// -3*i
                "-?i",                                    /// i sau -i
                "-?\\d+(\\.\\d+)?[+-]i"                   /// 2+i sau 2-i
        };

        for(int i = 0; i < args.length; i += 2)
            if(!args[i].matches(structura[0]) && !args[i].matches(structura[2])&&!args[i].matches(structura[3]))
                return false;

        for(int i = 1; i < args.length; i += 2)
            if(!args[i].matches("[-+/*]"))
                return false;

        return  true;
    }

    private NumarComplex[] buildArgs(String[] args)
    {
        int k = 0;
        NumarComplex[] numbers= new NumarComplex[args.length/2+1];
        String[] structura = {
                "-?\\d+(\\.\\d+)?[+-]\\d+(\\.\\d+)?\\*i", /// 2+3*i
                "-?\\d+(\\.\\d+)?",                       /// 2
                "-?\\d+(\\.\\d+)?\\*i",                   /// -3*i
                "-?i",                                    /// i sau -i
                "-?\\d+(\\.\\d+)?[+-]i"                   /// 2+i sau 2-i
        };
        for(int i = 0; i < args.length; i += 2)
        {
            double ParteReala = 0, ParteImaginara = 0;
            if(args[i].matches(structura[0]))
            {
                ParteReala = 1;
                ParteImaginara = 1;
                if(args[i].charAt(0) == '-') /// extrage minusul
                {
                    /// -2+3*i  2 + 3*i
                    ParteReala = -1;
                    args[i] = args[i].substring(1);
                }
                String[] nr = args[i].split("[+-]"); /// separa argumentele
                ParteReala = Double.parseDouble(nr[0])*ParteReala;
                if(args[i].contains("-"))
                    ParteImaginara = -1;
                ParteImaginara = Double.parseDouble(nr[1].substring(0,nr[1].length()-2)) * ParteImaginara;
            }
            else
                if(args[i].matches(structura[1]))
                {
                    ParteReala=1;
                    if(args[i].charAt(0) == '-')
                    {
                        ParteReala=-1;
                        args[i] = args[i].substring(1);
                    }
                    ParteReala = Double.parseDouble(args[i]) * ParteReala;
                }
                else
                    if(args[i].matches(structura[2]))
                    {
                        ParteImaginara = 1;
                        if(args[i].charAt(0) == '-')
                        {
                            ParteImaginara = -1;
                            args[i] = args[i].substring(1);
                        }
                        ParteImaginara = Double.parseDouble(args[i].substring(0,args[i].length()-2)) * ParteImaginara;
                    }
                    else
                        if(args[i].matches(structura[3]))
                        {
                            /// i sau -i
                            if(args[i].charAt(0) == '-')
                                ParteImaginara = -1;
                            else
                                ParteImaginara = 1;
                        }
                        else
                        {
                            ParteReala = 1;
                            if(args[i].charAt(0) == '-')
                            {
                                ParteReala = -1;
                                args[i] = args[i].substring(1);
                            }
                            String[] nr = args[i].split("[+-]");
                            ParteReala = Double.parseDouble(nr[0])*ParteReala;
                            if(args[i].contains("-"))
                                ParteImaginara = -1;
                            else
                                ParteImaginara = 1;
                        }
                        numbers[k++] = new NumarComplex(ParteReala,ParteImaginara);
        }
        return numbers;
    }

}
