package kidtoys.az.kidphone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by abdurrauf on 5/29/16.
 */
public class QuestionChooser implements Comparator<QuestionChooser.Question> {



    @Override
    public int compare(Question question, Question t1) {
        float percentageCorrect1=(float)(question.correctAnswers)/(float)question.totalQuestion;
        float percentageCorrect2=(float)(t1.correctAnswers)/(float)t1.totalQuestion;
        if(lastFound){

            return  percentageCorrect1>percentageCorrect2?-1:1;
        }else{
            return  percentageCorrect1<percentageCorrect2?-1:1;
        }
    }

    public static class Question{
        public int questionSoundId;
        public int correctAnswerButtonId;
        public int totalQuestion;
        public int correctAnswers;
    }

    public List<Question> questionList=new ArrayList<>();

    public boolean lastFound=false;
    public Question currentQuestion=null;

    /**
     * Add question. Warning: Make sure it is distinct  yourself
     * @param item
     */
    public void addQuestion(Question item){
        questionList.add(item);
    }

    /**
     * Mark current question correct if user found it
     */
    public void markCorrectlyFound(){
        if(currentQuestion!=null){
            currentQuestion.correctAnswers++;
            lastFound=true;
        }
    }

    public Question getNewQuestion(){
        Question q=null;
        //Shuffle questions
        Collections.shuffle(questionList);

        //choose one from 1/4
        int count=questionList.size()/4;
        if(count>0){
            //choose form 1/4 best match
            PriorityQueue<Question> questions=new PriorityQueue<>(count,this);
            for(int i=0;i<count;i++){
                questions.add(questionList.get(i));
            }
            q=questions.poll();

        }

        currentQuestion=q;
        lastFound=false;
        return q;
    }



}
