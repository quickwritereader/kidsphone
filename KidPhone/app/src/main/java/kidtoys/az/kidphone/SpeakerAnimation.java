package kidtoys.az.kidphone;

/**
 * Created by abdurrauf on 7/10/16.
 */
public class SpeakerAnimation extends BaseAnimation {


    int faceScene =0;
    int hole1=0;
    int hole2=1;
    long timeFace;
    long timeLine;
    public SpeakerAnimation(FunnyDisplay display) {
        super(display);
        timeLine=0;
        timeFace=0;
    }



    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;
         if(timeLine==0){
             surface.clear();
         }
        //line around
        if(timeLine+120<System.currentTimeMillis()) {
            drawn=true;
            timeLine=System.currentTimeMillis();
            int right=surface.getWidth()-1;
            int bottom=surface.getHeight()-1;
            surface.drawLine(0, 0, right , 0, FunnySurface.DotColor.White, FunnySurface.DotType.Star);
            surface.drawLine(0,bottom,right,bottom,  FunnySurface.DotColor.White, FunnySurface.DotType.Star);
            surface.drawLine(0,0,0,bottom, FunnySurface.DotColor.White, FunnySurface.DotType.Star);
            surface.drawLine(right,0,right,bottom, FunnySurface.DotColor.White, FunnySurface.DotType.Star);
            /**
             * gcd(20 15)=5
             * 5 lines with 3 visible and two black holes. we will move holes. (idea by qwr)
             *
             */
              //in top line there is 4 5dots so

             for (int i=0;i<5;i++){
                 surface.putDot(hole1+i*5,0, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                 surface.putDot(hole2+i*5,0, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
             }
            //in bottom also 4 5dots but reversed
            for (int i=0;i<5;i++){
                surface.putDot(5-hole1+i*5,bottom, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.putDot(5-hole2+i*5,bottom, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
            }
            //in left there is 3 5dots
            for (int i=0;i<3;i++){
                surface.putDot(0,5-hole1+i*5,  FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.putDot(0,5-hole2+i*5,  FunnySurface.DotColor.Black, FunnySurface.DotType.None);
            }
            //in right the same but reversed
            for (int i=0;i<3;i++){
                surface.putDot(right,hole1+i*5,  FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.putDot(right, hole2+i*5,  FunnySurface.DotColor.Black, FunnySurface.DotType.None);
            }
            //move hole positions
            hole1++;
            if(hole1>=5)hole1=0;
            hole2++;
            if(hole2>=5)hole2=0;

        }
        //face
        if(timeFace+200<System.currentTimeMillis()) {
            int center = surface.getWidth() / 2;
            drawn=true;
            timeFace=System.currentTimeMillis();
            //eyes
            surface.putDot(center - 2, 4, FunnySurface.DotColor.Yellow, FunnySurface.DotType.Circle);
            surface.putDot(center + 2, 4, FunnySurface.DotColor.Yellow, FunnySurface.DotType.Circle);
            //lips
            if (faceScene == 0) {
                surface.drawLine(center - 3, 8, center + 3, 8, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
                surface.drawLine(center - 3, 8, center, 9, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.drawLine(center, 9, center + 3, 8, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
            } else if (faceScene == 1) {
                surface.drawLine(center - 3, 8, center + 3, 8, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.drawLine(center - 3, 8, center, 9, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
                surface.drawLine(center, 9, center + 3, 8, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
            }
            faceScene++;
            if (faceScene > 1) faceScene = 0;
        }
        return  drawn;
    }
}
