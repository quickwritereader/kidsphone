package kidtoys.az.kidphone;

/**
 * Created by ramil on 2016-11-20.
 */

public class LetterAnimation extends BaseAnimation implements FunnySurface.CallbackDraw {


    private char Letter = ' ';
    private boolean isLetter = true;
    private FunnyButton.InnerShapeType innerShapeType = null;
    private RenderCallback currentClbk = null;

    public LetterAnimation(FunnyDisplay display) {
        super(display);
    }

    public void setInnerShapeType(FunnyButton.InnerShapeType innerShapeType) {
        this.innerShapeType = innerShapeType;
        isLetter = false;
    }

    public void setLetter(char letter) {
        Letter = letter;
        isLetter = true;
    }


    @Override
    protected boolean onDraw(FunnySurface surface) {
        return false;
    }

    @Override
    protected boolean isLooped() {
        return true;
    }

    private boolean timeAndRender(int time) {
        try {

            if (currentClbk != null) {
                currentClbk.RenderSurfaceOnMain();
            }else{
                return false;
            }
            long starting = System.currentTimeMillis();
            while (starting + time > System.currentTimeMillis()) {
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            currentClbk = null;
            return false;
        }
        return true;
    }

    @Override
    protected boolean onLoopDraw(FunnySurface surface, RenderCallback renderClbk) {
        this.currentClbk = renderClbk;
        surface.clear();
        if (!timeAndRender(150)) {
            return false;
        }
        if (isLetter) {
            int figureRandom = (int) (Math.random() * (FunnySurface.getMaxTypeSupport() - 1)) + 1;
            int colorRandom = (int) (Math.random() * (FunnySurface.getMaxColorSupport() - 2)) + 1;//exclude white and black

            FunnySurfaceUtils.drawChar(surface, surface.getWidth() / 2, 4, Letter, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.DotType.Circle, true, this);
        } else {
            int figureRandom = (int) (Math.random() * (FunnySurface.getMaxTypeSupport() - 1)) + 1;
            int colorRandom = (int) (Math.random() * (FunnySurface.getMaxColorSupport() - 2)) + 1;//exclude white and black
            /*FunnySurfaceUtils.drawFigure(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.supportedTypes[figureRandom], true);*/
            FunnySurfaceUtils.drawFigure(surface, surface.getWidth() / 2, 4, innerShapeType, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.DotType.Circle, true, this);
        }
        surface.clear();
        if (!timeAndRender(150)) {
            return false;
        }
        return false;
    }

    @Override
    public boolean renderStep() {

        return timeAndRender(150);
    }
}
