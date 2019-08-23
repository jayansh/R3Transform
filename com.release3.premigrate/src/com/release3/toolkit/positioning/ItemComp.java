package com.release3.toolkit.positioning;

import java.util.Comparator;

public class ItemComp implements Comparator<Element> {
    

    public static int STEP = 10; //precision
    private Object[] array;
    
    public ItemComp(Object[] arr){
        
        array=new Object[arr.length];
        for (int i=0; i< arr.length; i++) {
            array[i]=arr[i];
        }
    }


    private boolean findInner(Element e1, Element e2){
        int minY ;
        int minYDelta;
        int maxY;
        int maxYDelta;
        int minX ;
        int minXDelta;
        int maxX;
        int maxXDelta;
        boolean value=false;


        if (e1.getY() < e2.getY()){
            minY = e1.getY();
            minYDelta = e1.getYDelta();
            maxY = e2.getY();
            maxYDelta = e2.getYDelta();
        }else{
            minY = e2.getY();
            minYDelta = e2.getYDelta();
            maxY = e1.getY();
            maxYDelta = e1.getYDelta();
        }
          
        if (e1.getX() < e2.getX()){
            minX = e1.getX();
            minXDelta = e1.getXDelta();
            maxX = e2.getX();
            maxXDelta = e2.getXDelta();
        }else{
            minX = e2.getX();
            minXDelta = e2.getXDelta();
            maxX = e1.getX();
            maxXDelta = e1.getXDelta();
        }




        for (int i=0; i < array.length;i++){
            Element elem= (Element)array[i];   
            
            if ( (elem.getX()==e1.getX())&&(elem.getXDelta()==e1.getXDelta())&&
                 (elem.getY()==e1.getY())&&(elem.getYDelta()==e1.getYDelta()))
                 continue;

            if ( (elem.getX()==e2.getX())&&(elem.getXDelta()==e2.getXDelta())&&
                 (elem.getY()==e2.getY())&&(elem.getYDelta()==e2.getYDelta()))
                 continue;

            
//if((e1.getName().equals("NODBBLOCK"))&&(e2.getName().equals("POSTAL_CODE"))){
//System.out.println("+++++++++++++"+elem.getName()+"+++++++++++++++++++");
//System.out.println( elem.getX()/STEP +">=" +minX/STEP +"&&"+(elem.getX()+elem.getXDelta())/STEP+ "<" +maxX/STEP);
//System.out.println((elem.getY()+elem.getYDelta())/STEP+ ">"+ minY/STEP +"&&"+ elem.getY()/STEP +"<"+ (minY+minYDelta)/STEP);
//System.out.println((elem.getY()+elem.getYDelta())/STEP +">="+ maxY/STEP);
//}


           if (
                    ((elem.getX()/STEP >= minX/STEP)&&((elem.getX()+elem.getXDelta())/STEP < maxX/STEP))&&
                    (((elem.getY()+elem.getYDelta())/STEP > minY/STEP)&&(elem.getY()/STEP < (minY+minYDelta)/STEP))&&
                    ((elem.getY()+elem.getYDelta())/STEP > maxY/STEP)
           )  {
               
                value=true;
                break;
           }
        }
        return value; 
    }



    public int compare(Element e1, Element e2) {

        if( findInner(e1,e2) ){

//if((e1.getName().equals("NODBBLOCK"))&&(e2.getName().equals("POSTAL_CODE"))){
//System.out.println((e1.getY()+e1.getYDelta())/STEP+ "<"+ e2.getY()/STEP);
//}        

            if (e1.getX() < e2.getX()){
debug(e1,e2,-1,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
             return -1;
            }else{
debug(e1,e2,1,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                return 1;
                
            }
        }

        if (e1.getX()/STEP < e2.getX()/STEP ){
            if(e1.getY()/STEP >= (e2.getY() + e2.getYDelta())/STEP){
debug(e1,e2,1,"");
               return 1;
            }   
            else{
debug(e1,e2,-1,"");
               return -1;
            }    
        }

        if ((e1.getX()/STEP >= e2.getX()/STEP )&&(e1.getX()/STEP < (e2.getX() + e2.getXDelta())/STEP)){     

            if(e1.getY()/STEP >= (e2.getY() + e2.getYDelta())/STEP){
debug(e1,e2,1,"");
               return 1;
            }  
            if((e1.getY()+e1.getYDelta())/STEP <= e2.getY()/STEP){
debug(e1,e2,-1,"");
               return -1;
            }   
            if((e1.getY()/STEP >= e2.getY()/STEP)&&(e1.getY()/STEP < (e2.getY() + e2.getYDelta())/STEP)){
debug(e1,e2,0,"");
               return 0;
            }   
        }


        if (e1.getX()/STEP >= (e2.getX() + e2.getXDelta())/STEP){
          if((e1.getY()+e1.getYDelta())/STEP <= e2.getY()/STEP){
debug(e1,e2,-1,"");
             return -1;
          }   
          else{
debug(e1,e2,1,"");
             return 1;
          }   
        }

//      System.err.println( "-----------------------------------------------------------------------------------------------");        
//      System.err.println( e1.getX()/STEP+ ">=" +((e2.getX()+e2.getXDelta())/STEP));        
//      System.err.println( e1.getX()/STEP +">="+ e2.getX()/STEP +"&&" +e1.getX()/STEP+ "< " +(e2.getX() + e2.getXDelta())/STEP);
//      System.err.println( e1.getX()/STEP+"< "+ e2.getX()/STEP );
//      System.err.println( e1.getY()/STEP +">"+ (e2.getY() + e2.getYDelta())/STEP);        
//      System.err.println( e1.getY()/STEP +"<"+ e2.getY()/STEP);
//      System.err.println( e1.getY()/STEP +" >= "+e2.getY()/STEP +"&&" + e1.getY()/STEP +"<"+ (e2.getY() + e2.getYDelta())/STEP) ;


      System.err.println( "Error comparizon");        


      return 0;  
    }
   private void debug(Element e1, Element e2,int i, String Inner){
//       System.err.println( "type=" + e1.getElementType()+" Name="+e1.getName() + " e1.X =" +e1.getX()+ "  e1.dX ="+e1.getXDelta()+ "  e1.Y="+e1.getY()+"  e1.dY="+e1.getYDelta()+"\n" +        
//                            "type=" + e2.getElementType()+" Name="+e2.getName() +  " e2.X =" +e2.getX()+ "  e2.dX ="+e2.getXDelta()+ "  e2.Y="+e2.getY()+"  e2.dY="+e2.getYDelta() +"\n"+        
//                            i+ "Iner = ?"+Inner+"\n"+
//                            "-----------------------------------------------------------------------------------------------");        
   }
    
}
