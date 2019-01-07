package kidtoys.az.kidphone;



import java.util.ArrayList;

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
        float percentageCorrect1=0;
        if(question.totalQuestion>0)percentageCorrect1=(float)(question.correctAnswers)/(float)question.totalQuestion;
        float percentageCorrect2=0;
        if(t1.totalQuestion>0)percentageCorrect2=(float)(t1.correctAnswers)/(float)t1.totalQuestion;
        //Log.d("question","q1 "+percentageCorrect1+ " q2 "+percentageCorrect2);
        if(lastFound){
            return  percentageCorrect1<percentageCorrect2?-1:1;
        }else{
            return  percentageCorrect1>percentageCorrect2?-1:1;
        }
    }

    public static class Question{
        public int questionSoundId;
        public int questionId;
        public int totalQuestion;
        public int correctAnswers;
        public Question (int soundId,int questionId){
            this.questionSoundId=soundId;
            this.questionId=questionId;
        }
    }

    public List<Question> questionList=new ArrayList<>();

    public boolean lastFound=false;
    private Question currentQuestion=null;

    public  Question getCurrentQuestion(){
        return  currentQuestion;
    }

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
        }else{
            lastFound=false;
        }
        currentQuestion=null;
    }

    public void markWronglyFound(){
        lastFound=false;
        currentQuestion=null;
    }

    public void markSkipped(){
        if(currentQuestion!=null){
            if(currentQuestion.totalQuestion>0) currentQuestion.totalQuestion--;
        }
        currentQuestion=null;
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
                Question x=questionList.get(i);
               // Log.d("question","c: "+x.correctAnswers+ " t: "+x.totalQuestion);
                questions.add(x);
            }
            q=questions.poll();
            q.totalQuestion++;

        }

        currentQuestion=q;
        lastFound=false;
        return q;
    }



}
