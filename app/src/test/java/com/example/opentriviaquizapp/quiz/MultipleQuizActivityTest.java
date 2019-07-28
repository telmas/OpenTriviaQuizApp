package com.example.opentriviaquizapp.quiz;

import com.example.opentriviaquizapp.system.SystemController;
import com.example.opentriviaquizapp.userprofile.entities.BooleanQuestion;
import com.example.opentriviaquizapp.userprofile.entities.MultipleQuestion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MultipleQuizActivityTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Setting up class: Set username");
        SystemController systemController = SystemController.getINSTANCE();
        systemController.setUserName("TEDI");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Tearing down class: Remove username");
        SystemController systemController = SystemController.getINSTANCE();
        systemController.setUserName(null);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("Set up: Setting up mock questions and answers");

        ArrayList<BooleanQuestion> booleanQuestions = new ArrayList<>();
        ArrayList<MultipleQuestion> multipleQuestions = new ArrayList<>();

        ArrayList<Boolean> booleanAnswers = new ArrayList<>();
        ArrayList<Boolean> booleanAnswersHaveBeenSet = new ArrayList<>();
        ArrayList<String> stringAnswers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            booleanQuestions.add(new BooleanQuestion("BooleanQuestion"+i,true));
            booleanAnswersHaveBeenSet.add(true);
            booleanAnswers.add(true);
            multipleQuestions.add(new MultipleQuestion("MultipleQuestion"+i, "Answer"+i));
            stringAnswers.add("Answer"+i);
        }

        SystemController systemController = SystemController.getINSTANCE();

        systemController.setBooleanQuestions(booleanQuestions);
        systemController.setMultipleQuestions(multipleQuestions);

        systemController.setBooleanAnswersHaveBeenSet(booleanAnswersHaveBeenSet);
        systemController.setBooleanAnswers(booleanAnswers);
        systemController.setStringAnswers(stringAnswers);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tear down: Removing system controller variables");
        SystemController systemController = SystemController.getINSTANCE();

        systemController.setBooleanQuestions(null);
        systemController.setMultipleQuestions(null);

        systemController.setBooleanAnswersHaveBeenSet(null);
        systemController.setBooleanAnswers(null);
        systemController.setStringAnswers(null);
    }

    @Test
    public void getScore() throws Exception {
        System.out.println("Testing \"getScore()\" method of MultipleQuizActivity class");
        MultipleQuizActivity multipleQuizActivity = new MultipleQuizActivity();
        System.out.println("Expected perfect score");
        assertEquals(10,  multipleQuizActivity.getScore());
    }
}