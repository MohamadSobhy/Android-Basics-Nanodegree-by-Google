package com.example.mohamedsobhy.quizapp;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup11 , radioGroup12 , radioGroup13 ,
                       radioGroup31 , radioGroup32 , radioGroup33 ,
                       radioGroup41 , radioGroup42 , radioGroup43 ,
                       radioGroup51 , radioGroup52 , radioGroup53 ,
                       radioGroup61 , radioGroup62;

    private CheckBox checkBox71 , checkBox72 , checkBox73 , checkBox74;

    private RadioButton checkedRadioButton;
    private int grade;
    private TextView gradeTextView;
    private EditText question2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning all variable ..
        assignAllLocalVariable();

        //setting the OnChangeListener of all RadioGroups ..
        setRadioGroupsOnCheckedChangeLisener();
    }

    /**
     * gets the answers from the user and checks if it correct or not ..
     * @param view carrys the submit button which clicked to submit answers.
     */
    public void validateUserAnswers(View view){

        String questionAnswer = null;

        //validate the answer of the first question ..

        checkedRadioButton = (RadioButton) findViewById(radioGroup11.getCheckedRadioButtonId());
        if(checkedRadioButton == null){
            checkedRadioButton = (RadioButton) findViewById(radioGroup12.getCheckedRadioButtonId());
            if(checkedRadioButton == null){
                checkedRadioButton = (RadioButton) findViewById(radioGroup13.getCheckedRadioButtonId());
            }
        }
        if(checkedRadioButton != null){
            questionAnswer = checkedRadioButton.getText().toString();
            if(questionAnswer == getResources().getString(R.string.question1_correct_choice)){
                grade += 10;
                checkedRadioButton.setButtonDrawable(R.drawable.correct_answer_check_box);
            }
        }

        //validate the answer of question 2 ..
        questionAnswer = question2EditText.getText().toString().toUpperCase().trim();

        if( questionAnswer.equals(getResources().getString(R.string.question2_first_correct_answer ).toUpperCase())||
                questionAnswer.equals(getResources().getString(R.string.question2_second_correct_answer).toUpperCase()) ||
                questionAnswer.equals(getResources().getString(R.string.question2_third_correct_answer).toUpperCase())
                ){

            grade += 10;

        }

        //validate the answer of question 3 ..
        checkedRadioButton = (RadioButton) findViewById(radioGroup31.getCheckedRadioButtonId());
        if(checkedRadioButton == null){
            checkedRadioButton = (RadioButton) findViewById(radioGroup32.getCheckedRadioButtonId());
            if(checkedRadioButton == null){
                checkedRadioButton = (RadioButton) findViewById(radioGroup33.getCheckedRadioButtonId());
            }
        }
        if(checkedRadioButton != null){
            questionAnswer = checkedRadioButton.getText().toString();
            if(questionAnswer == getResources().getString(R.string.question3_correct_choice)){
                grade += 10;
                checkedRadioButton.setButtonDrawable(R.drawable.correct_answer_check_box);
            }
        }

        //validate the answer of question 4 ..
        checkedRadioButton = (RadioButton) findViewById(radioGroup41.getCheckedRadioButtonId());
        if(checkedRadioButton == null){
            checkedRadioButton = (RadioButton) findViewById(radioGroup42.getCheckedRadioButtonId());
            if(checkedRadioButton == null){
                checkedRadioButton = (RadioButton) findViewById(radioGroup43.getCheckedRadioButtonId());
            }
        }
        if(checkedRadioButton != null){
            questionAnswer = checkedRadioButton.getText().toString();
            if(questionAnswer == getResources().getString(R.string.question4_correct_choice)){
                grade += 10;
                checkedRadioButton.setButtonDrawable(R.drawable.correct_answer_check_box);
            }
        }

        //validate the answer of question 5 ..
        checkedRadioButton = (RadioButton) findViewById(radioGroup51.getCheckedRadioButtonId());
        if(checkedRadioButton == null){
            checkedRadioButton = (RadioButton) findViewById(radioGroup52.getCheckedRadioButtonId());
            if(checkedRadioButton == null){
                checkedRadioButton = (RadioButton) findViewById(radioGroup53.getCheckedRadioButtonId());
            }
        }
        if(checkedRadioButton != null){
            questionAnswer = checkedRadioButton.getText().toString();
            if(questionAnswer == getResources().getString(R.string.question5_correct_choice)){
                grade += 10;
                checkedRadioButton.setButtonDrawable(R.drawable.correct_answer_check_box);
            }
        }

        //validate the answer of question 3 ..
        checkedRadioButton = (RadioButton) findViewById(radioGroup61.getCheckedRadioButtonId());
        if(checkedRadioButton == null){
            checkedRadioButton = (RadioButton) findViewById(radioGroup62.getCheckedRadioButtonId());
        }
        if(checkedRadioButton != null){
            questionAnswer = checkedRadioButton.getText().toString();
            if(questionAnswer == getResources().getString(R.string.question6_correct_choice)){
                grade += 10;
                checkedRadioButton.setButtonDrawable(R.drawable.correct_answer_check_box);
            }
        }

        if(checkBox71.isChecked() && checkBox72.isChecked() && checkBox73.isChecked() && checkBox74.isChecked()){
            grade += 10;
            checkBox71.setButtonDrawable(R.drawable.correct_answer_check_box);
            checkBox72.setButtonDrawable(R.drawable.correct_answer_check_box);
            checkBox73.setButtonDrawable(R.drawable.correct_answer_check_box);
            checkBox74.setButtonDrawable(R.drawable.correct_answer_check_box);
        }

        if(grade >= 35) {
            Toast.makeText(this, "You Passed.\nYour Grade is : " + grade, Toast.LENGTH_LONG).show();
            gradeTextView.setText("Your Grade is : " + grade);
        }else{
            gradeTextView.setText("Your Grade is : " + grade);
            Toast.makeText(this, "You Failed.\nYour Grade is : " + grade, Toast.LENGTH_LONG).show();
        }
        grade = 0;

    }

    /**
     * reset UI to default;
     * @param view
     */
    public void resetAppUI(View view){

        //resetting the contentView of the Activity to default ..
        setContentView(R.layout.activity_main);

        //assigning all variable ..
        assignAllLocalVariable();

        //setting the OnChangeListener of all RadioGroups ..
        setRadioGroupsOnCheckedChangeLisener();
    }

    private void assignAllLocalVariable(){

        //assign the textView if the Score ..
        gradeTextView = (TextView) findViewById(R.id.score_text_view);

        question2EditText = (EditText) findViewById(R.id.question2_edit_text);

        //Question 1 Radio Buttons ..
        radioGroup11 = (RadioGroup) findViewById(R.id.radio_group11);
        radioGroup12 = (RadioGroup) findViewById(R.id.radio_group12);
        radioGroup13 = (RadioGroup) findViewById(R.id.radio_group13);

        //Question 3 Radio Buttons ..
        radioGroup31 = (RadioGroup) findViewById(R.id.radio_group31);
        radioGroup32 = (RadioGroup) findViewById(R.id.radio_group32);
        radioGroup33 = (RadioGroup) findViewById(R.id.radio_group33);

        //Question 4 Radio Buttons ..
        radioGroup41 = (RadioGroup) findViewById(R.id.radio_group41);
        radioGroup42 = (RadioGroup) findViewById(R.id.radio_group42);
        radioGroup43 = (RadioGroup) findViewById(R.id.radio_group43);

        //Question 5 Radio Buttons ..
        radioGroup51 = (RadioGroup) findViewById(R.id.radio_group51);
        radioGroup52 = (RadioGroup) findViewById(R.id.radio_group52);
        radioGroup53 = (RadioGroup) findViewById(R.id.radio_group53);

        //Question 5 Radio Buttons ..
        radioGroup61 = (RadioGroup) findViewById(R.id.radio_group61);
        radioGroup62 = (RadioGroup) findViewById(R.id.radio_group62);

        //Question 7 CheckBoxes
        checkBox71 = (CheckBox)findViewById(R.id.question7_first_box);
        checkBox72 = (CheckBox)findViewById(R.id.question7_second_box);
        checkBox73 = (CheckBox)findViewById(R.id.question7_third_box);
        checkBox74 = (CheckBox)findViewById(R.id.question7_fourth_box);

    }
    private void setRadioGroupsOnCheckedChangeLisener(){

        // setting listener for Question 1 Radio Buttons ..
        radioGroup11.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup12.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup13.setOnCheckedChangeListener(new OnCheckedChange());

        // setting listener for Question 3 Radio Buttons ..
        radioGroup31.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup32.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup33.setOnCheckedChangeListener(new OnCheckedChange());

        // setting listener for Question 4 Radio Buttons ..
        radioGroup41.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup42.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup43.setOnCheckedChangeListener(new OnCheckedChange());

        // setting listener for Question 5 Radio Buttons ..
        radioGroup51.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup52.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup53.setOnCheckedChangeListener(new OnCheckedChange());

        // setting listener for Question 6 Radio Buttons ..
        radioGroup61.setOnCheckedChangeListener(new OnCheckedChange());
        radioGroup62.setOnCheckedChangeListener(new OnCheckedChange());

    }

    /**
     * this class is helper class for handling multiple RadioGroups and treat them as single RadioGroup ..
     */
    private class OnCheckedChange implements RadioGroup.OnCheckedChangeListener {

        /**
         * this function called is the checked state of the RadioGroup is changed ..
         * @param radioGroup  the RadioGroup whose checked state is changed ..
         * @param i  the identifier of the newly checked radio button.
         */
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            switch (radioGroup.getId()){

                //this handle the check change in Question 1 RadioGroup ..
                case R.id.radio_group11:{

                    //clearing the group from listener before clearing check ..
                    radioGroup12.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup12.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup12.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup13.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup13.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup13.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group12:{

                    //clearing the group from listener before clearing check ..
                    radioGroup11.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup11.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup11.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup13.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup13.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup13.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group13:{

                    //clearing the group from listener before clearing check ..
                    radioGroup11.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup11.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup11.setOnCheckedChangeListener(this);


                    //clearing the group from listener before clearing check ..
                    radioGroup12.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup12.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup12.setOnCheckedChangeListener(this);
                    break;
                }

                //this handle the check change in Question 3 RadioGroup ..
                case R.id.radio_group31:{

                    //clearing the group from listener before clearing check ..
                    radioGroup32.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup32.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup32.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup33.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup33.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup33.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group32:{

                    //clearing the group from listener before clearing check ..
                    radioGroup31.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup31.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup31.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup33.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup33.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup33.setOnCheckedChangeListener(this);

                    break;

                }
                case R.id.radio_group33:{

                    //clearing the group from listener before clearing check ..
                    radioGroup31.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup31.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup31.setOnCheckedChangeListener(this);


                    //clearing the group from listener before clearing check ..
                    radioGroup32.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup32.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup32.setOnCheckedChangeListener(this);
                    break;
                }

                //this handle the check change in Question 4 RadioGroup ..
                case R.id.radio_group41:{

                    //clearing the group from listener before clearing check ..
                    radioGroup42.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup42.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup42.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup43.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup43.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup43.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group42:{

                    //clearing the group from listener before clearing check ..
                    radioGroup41.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup41.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup41.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup43.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup43.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup43.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group43:{

                    //clearing the group from listener before clearing check ..
                    radioGroup41.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup41.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup41.setOnCheckedChangeListener(this);


                    //clearing the group from listener before clearing check ..
                    radioGroup42.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup42.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup42.setOnCheckedChangeListener(this);
                    break;
                }

                //this handle the check change in Question 1 RadioGroup ..
                case R.id.radio_group51:{

                    //clearing the group from listener before clearing check ..
                    radioGroup52.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup52.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup52.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup53.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup53.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup53.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group52:{

                    //clearing the group from listener before clearing check ..
                    radioGroup51.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup51.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup51.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup53.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup53.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup53.setOnCheckedChangeListener(this);

                    break;
                }
                case R.id.radio_group53:{

                    //clearing the group from listener before clearing check ..
                    radioGroup51.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup51.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup51.setOnCheckedChangeListener(this);

                    //clearing the group from listener before clearing check ..
                    radioGroup52.setOnCheckedChangeListener(null);
                    //clearing checks of the RadioGroup ..
                    radioGroup52.clearCheck();
                    //resetting the listener th the RadioGroup ..
                    radioGroup52.setOnCheckedChangeListener(this);
                    break;
                }
                //this handle the check change in Question 6 RadioGroup ..
                case R.id.radio_group61:{
                    radioGroup62.setOnCheckedChangeListener(null);
                    radioGroup62.clearCheck();
                    radioGroup62.setOnCheckedChangeListener(this);
                    break;
                }
                case R.id.radio_group62:{
                    radioGroup61.setOnCheckedChangeListener(null);
                    radioGroup61.clearCheck();
                    radioGroup61.setOnCheckedChangeListener(this);
                    break;
                }
            }
        }
    }
}
