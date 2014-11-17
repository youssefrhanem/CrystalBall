package com.rhanem.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private CrystalBall mCrystalBall = new CrystalBall();
    private TextView mAnswerLabel;
    // private Button mGetAnswerbutton;
    private ImageView mCrystalBallImage;

    // for shaking phone
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    /**
     * Called when the activity is first created.
     */

    //Add animation to the imageview which is the imageView1 here
    private void animateCrystalBall () {
        mCrystalBallImage.setImageResource(R.drawable.ball_animation);
        AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
        if(ballAnimation.isRunning()) {
            ballAnimation.stop();
        }
        ballAnimation.start();
    }

    private void animateAnswer(){
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(1500);
        //make the text stick after the animation
        fadeInAnimation.setFillAfter(true);
        // this line attaches the animation to the text view
        mAnswerLabel.setAnimation(fadeInAnimation);
    }

    // MediaPlayer Class To Play Sound file on Res Raw folder
    private void playSound(){
        MediaPlayer player = MediaPlayer.create(this,R.raw.crystal_ball);
        //start the sound
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void handleNewAnswer() {
        String answer = mCrystalBall.getAnswer();
        mAnswerLabel.setText(answer);
        animateCrystalBall();
        animateAnswer();
        playSound();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Assign the views from the layout file
        //mGetAnswerbutton = (Button) findViewById(R.id.getAnswerbutton);
        mAnswerLabel = (TextView) findViewById(R.id.answerLabel);
        mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);

        // for shaking
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector =  new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleNewAnswer();
            }
        });

     /*   View.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleNewAnswer();
            }
        };
        mGetAnswerbutton.setOnClickListener(listner);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }
}
