package kidtoys.az.kidphone;

/**
 * Question mode
 */
public class QuestionMode extends BaseMode implements  SoundCallBack{

    private QuestionChooser quizGiver;
    private static String letters="ABCÇDEƏFGĞHIİJKQLMNOÖPRSŞTUÜVYXZ";
    private static String numbers="0123456789";
    private int [] corrects={R.raw.az_correct_1,R.raw.az_correct_2,R.raw.az_correct_3};
    private int [] wrongs={R.raw.az_wrong_1,R.raw.az_wrong_2,R.raw.az_wrong_3};
    private int correctCount=0;
    private int wrongCount=0;

    public QuestionMode (Phone phone) throws Exception{
        super(phone);
        quizGiver=new QuestionChooser();
        for (int i=0;i<letters.length();i++){
            quizGiver.addQuestion(new QuestionChooser.Question(R.raw.az_question_alphabet,i));
        }
        for (int i=0;i<numbers.length();i++){
            quizGiver.addQuestion(new QuestionChooser.Question(R.raw.az_question_number,i));
        }
        //first and last 4 skipped
        for (int i=1;i<FunnyButton.InnerShapeType.values().length-4;i++){
            quizGiver.addQuestion(new QuestionChooser.Question(R.raw.az_question_figure,i));
        }
        askNew();
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        QuestionChooser.Question x=quizGiver.getCurrentQuestion();
       if(x!=null){
           boolean found=false;
           switch(x.questionSoundId){
               case R.raw.az_question_alphabet: {
                   if(funnyButton.getKeyMode()==FunnyButton.KeyMode.Letters){
                       char letter=letters.charAt(x.questionId);
                       if (letter=='Ə') letter='ə';
                       String buttonLetter=funnyButton.getLettersText();
                       if(  buttonLetter.indexOf(letter) >=0){
                           found=true;
                       }
                   }
               }
               break;
               case R.raw.az_question_number: {
                   if(funnyButton.getKeyMode()==FunnyButton.KeyMode.Numbers){
                       char number=numbers.charAt(x.questionId);
                       String buttonLetter=funnyButton.getNumbersText();
                       if(  buttonLetter.indexOf(number) >=0){
                           found=true;
                       }
                   }
               }
               break;
               case R.raw.az_question_figure:{
                   if(funnyButton.getKeyMode()==FunnyButton.KeyMode.Figures){
                       FunnyButton.InnerShapeType figure=FunnyButton.InnerShapeType.values()[x.questionId];
                        if(figure==funnyButton.getInnerShape()){
                            found=true;
                        }
                   }
               }
               break;
           }
           int id;

           FunnySurface surface=phone.getDisplay().getMainSurface();
           surface.clear();
           if(found){
               if(correctCount>=corrects.length)correctCount=0;
                 id=corrects[correctCount++];
               FunnySurfaceUtils.drawCorrect(surface,surface.getWidth() / 2, 4,true);

               quizGiver.markCorrectlyFound();
           }else{
               if(wrongCount>=wrongs.length)wrongCount=0;
               id=wrongs[wrongCount++];
               FunnySurfaceUtils.drawChar(surface,surface.getWidth() / 2, 4,'X', FunnySurface.DotColor.Red, FunnySurface.DotType.Star, true);
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
    public   void onRefresh(){
        quizGiver.markSkipped();
        askNew();
    }


    private void play_figure(FunnyButton.InnerShapeType innerShapeType) {

        phone.getDisplay().drawFigure(innerShapeType);
        int duration = phone.getAudio().playFigures(innerShapeType);
        phone.refreshActiveTime(duration);
    }


    private void play(char l) {
        phone.getDisplay().drawChar(l) ;
        int duration=phone.getAudio().playChar(l);
        phone.refreshActiveTime(duration);
    }

    private void askNew() {
        QuestionChooser.Question x=quizGiver.getNewQuestion();
        switch(x.questionSoundId){
            case R.raw.az_question_alphabet: {
                phone.changeKeys(FunnyButton.KeyMode.Letters);
                phone.getAudio().PlayMp3(x.questionSoundId,this);

            }
                break;
            case R.raw.az_question_number: {
                phone.changeKeys(FunnyButton.KeyMode.Numbers);
                phone.getAudio().PlayMp3(x.questionSoundId,this);
            }
                break;
            case R.raw.az_question_figure:{
                phone.changeKeys(FunnyButton.KeyMode.Figures);
                phone.getAudio().PlayMp3(x.questionSoundId,this);
            }
                break;
        }
        phone.startSpeaker();
    }

    @Override
    public void onSave(){
        phone.stopSpeaker();
        phone.getAudio().StopMp3();

    }

    @Override
    public void soundPlayFinished() {
        QuestionChooser.Question x=quizGiver.getCurrentQuestion();
        phone.stopSpeaker();
        if(x!=null){
            switch(x.questionSoundId){
                case R.raw.az_question_alphabet: {
                    play(letters.charAt(x.questionId));
                }
                break;
                case R.raw.az_question_number: {
                    play(numbers.charAt(x.questionId));
                }
                break;
                case R.raw.az_question_figure:{
                    play_figure(FunnyButton.InnerShapeType.values()[x.questionId]);
                }
                break;
            }
        }else {
            askNew();
        }
    }
}
