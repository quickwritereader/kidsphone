package kidtoys.az.kidphone;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import static kidtoys.az.kidphone.FunnyButton.KeyMode.Figures;
import static kidtoys.az.kidphone.FunnyButton.KeyMode.Letters;
import static kidtoys.az.kidphone.FunnyButton.KeyMode.Numbers;

/**
 * Question mode
 */
public class QuestionMode extends BaseMode implements  SoundCallBack{

    private static final String TAG = "QuestionMode" ;
    private final QuestionChooser quizGiver;
    private final int [] corrects={R.raw.az_correct_1,R.raw.az_correct_2,R.raw.az_correct_3};
    private final int [] wrongs={R.raw.az_wrong_1,R.raw.az_wrong_2,R.raw.az_wrong_3};
    private int correctCount=0;
    private int wrongCount=0;

    public QuestionMode (Phone phone) throws Exception{
        super(phone);
        quizGiver=new QuestionChooser();
        ViewGroup keysGroup = phone.getKeysGroup();
        int childCount = keysGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {                                               
            View v = keysGroup.getChildAt(i);
            if (v instanceof FunnyButton) {
                FunnyButton funnyButton = (FunnyButton) keysGroup.getChildAt(i);
                if (funnyButton.getKeyMode() != FunnyButton.KeyMode.System) {
                     //prepate question
                    //answer will be itsnumber
                    String number = funnyButton.getNumbersText();
                    int answer = Integer.parseInt(number);
                    String letters = funnyButton.getLettersText();
                    for (int j=0;j<letters.length();j++) {
                        quizGiver.addQuestion(new QuestionChooser.Question(Letters, letters.charAt(j),answer));
                    }
                    if(number.length()>0) quizGiver.addQuestion(new QuestionChooser.Question(Numbers, number.charAt(0),answer));
                    quizGiver.addQuestion(new QuestionChooser.Question(Figures,funnyButton.getInnerShape().ordinal(),answer));
                }
            }
        }

        askNew();
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        QuestionChooser.Question x=quizGiver.getCurrentQuestion();
        int id;
       if(x!=null && funnyButton.getKeyMode()!= FunnyButton.KeyMode.System){
           int currentAnswer = Integer.parseInt(funnyButton.getNumbersText());
           boolean found= (x.questionAnswer == currentAnswer);

           FunnySurface surface=phone.getDisplay().getSurface();
           surface.clear();
           if(found){
               if(correctCount>=corrects.length)correctCount=0;
               id=corrects[correctCount++];
               FunnySurfaceUtils.drawSymbol(surface,surface.getWidth() / 2, surface.getHeight() / 2,found, FunnySurface.DotColor.Green, FunnySurface.DotType.Hexagon,true);
               quizGiver.markCorrectlyFound();
           }else{
               if(wrongCount>=wrongs.length)wrongCount=0;
               id=wrongs[wrongCount++];
               FunnySurfaceUtils.drawSymbol(surface,surface.getWidth() / 2, surface.getHeight() / 2,found, FunnySurface.DotColor.Red, FunnySurface.DotType.Hexagon, true);
               quizGiver.markWronglyFound();
           }
           phone.getDisplay().render();

           if(id>=0){
                phone.getAudio().PlayMp3(id,this);
           }
       }else{
           quizGiver.markSkipped();
           askNew();
       }
    }


    @Override
    public void onRefresh(){
        super.onRefresh();
        quizGiver.markSkipped();
        askNew();
    }


    private void play_figure(FunnyButton.InnerShapeType innerShapeType) {

        drawFigure(innerShapeType);
        int duration = phone.getAudio().playFigures(innerShapeType);
        phone.refreshActiveTime(duration);
    }

    private void drawFigure(FunnyButton.InnerShapeType innerShapeType) {
        FunnyDisplayBase display=phone.getDisplay();
        if(display!=null  ) {
           display.getSurface().drawFigure(innerShapeType);
           display.render();
        }
    }


    private void play(char l) {
        //Log.d(TAG,"play and draw");
        FunnyDisplayBase display=phone.getDisplay();
        if(display!=null  ) {
            display.getSurface().drawChar(l);
            display.render();
        }
        int duration=phone.getAudio().playChar(l);
        phone.refreshActiveTime(duration);
    }

    private void askNew() {
        QuestionChooser.Question x=quizGiver.getNewQuestion();
        phone.changeKeys(x.questionModeId);
        switch(x.questionModeId){
            case Letters:
                phone.getAudio().PlayMp3(R.raw.az_question_alphabet,this);
                break;
            case Numbers:
                phone.getAudio().PlayMp3(R.raw.az_question_number,this);
                break;
            case Figures:
                phone.getAudio().PlayMp3(R.raw.az_question_figure,this);
                break;
        }
        phone.startSpeaker();
    }

    @Override
    public void onSave(){
        super.onSave();

    }

    @Override
    public void soundPlayFinished() {
        phone.stopSpeaker(true);
        QuestionChooser.Question x=quizGiver.getCurrentQuestion();
        if(x!=null){
            switch(x.questionModeId){
                case Letters:
                case Numbers: {
                    play((char)x.questionAsk);
                }
                break;
                case Figures:{
                    play_figure(FunnyButton.InnerShapeType.values()[(int)x.questionAsk]);
                }
                break;
            }
        }else {
            askNew();
        }
    }
}
