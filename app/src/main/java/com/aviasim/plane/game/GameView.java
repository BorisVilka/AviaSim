package com.aviasim.plane.game;

import android.content.Context;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.subjects.PublishSubject;


public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private Paint bitmapPaint, text;
    public boolean paused;
    private Bitmap plane, bg;
    private int cor;
    private boolean front;
    public int score;
    public int count = 0;
    Paint paint;
    int progr = 0;
    private MediaPlayer sound = null;
    private MediaPlayer music = null;
    private int m = 0;

    public GameView(Context context, AttributeSet set) {
        super(context,set);
        music = MediaPlayer.create(context,R.raw.bg);
        music.setOnCompletionListener(mp -> mp.start());
        sound = MediaPlayer.create(context,R.raw.sound);
        paused = false;
        score = 0;
        front = false;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        score = context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("count",500);
        int id = context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("id",R.drawable.avia1);
        m = context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("sounds",0);
        text = new Paint();
        text.setTextSize(80);
        text.setColor(Color.WHITE);
        plane = BitmapFactory.decodeResource(getResources(),id);
        plane = Bitmap.createScaledBitmap(plane,plane.getWidth()/4,plane.getHeight()/4,true);
        bg = BitmapFactory.decodeResource(getResources(),R.drawable.bg2);
        bg = Bitmap.createScaledBitmap(bg,bg.getWidth(),bg.getHeight(),true);


        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(m==2) music.stop();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();
                cor = canvas.getWidth()-plane.getWidth();
                if(m==2) {
                    music.start();
                }
                if (canvas != null) {
                    draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

        });

        Thread updateThread = new Thread() {
            public void run() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.d("TAG","jjjjjjj"+progr);
                        if(fir) {
                            progr++;
                            progr = Math.min(progr,100);
                            subject.onNext(progr);
                        }
                        update.run();
                    }
                }, 100, 16);
            }
        };

        updateThread.start();

    }
    boolean fir = false;
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(paused) fir = true;
                break;
            case MotionEvent.ACTION_UP:
                if(paused) {
                    fir = false;
                    if(m>0) sound.start();
                }
                if(millis>=progr) {
                    score += 10 *c;
                    score = Math.max(score,0);
                    listener.end();
                }
                togglePause();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG",paused+"");
                if(paused) subject.onNext(0);
                break;
        }
        postInvalidate();
        return true;
    }
    Random random = new Random();
    int millis = 0;
    float c = 0;
    int x = 0, y = 0;
    float[] r = new float[]{0.02f, 0.01f,0.04f,0.03f};
    Runnable update = new Runnable() { //draws and updates bird and pipes
        @Override
        public void run() {
            boolean isEnds = false;
           try {
                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(bg,0,0,bitmapPaint);
                if(!paused) {
                    if(millis<=progr) {
                        x+=10;
                        y= (int) (x*Math.max((100-progr),30)/100);
                        c+=r[random.nextInt(r.length)];
                    } else if(millis>progr+130) {
                        isEnds = true;
                    }
                    millis++;
                    Matrix matrix = new Matrix();
                    matrix.reset();
                    if(!isEnds) matrix.preRotate(-(millis>progr ? progr : millis));
                    else matrix.preRotate(tmp);
                    tmp = Math.min(tmp,-(millis>progr ? progr : millis));
                    matrix.postTranslate(x,canvas.getHeight()-y-plane.getHeight());
                    canvas.drawBitmap(plane,matrix,bitmapPaint);
                    paint.setShader(new LinearGradient(0, 0, 0, canvas.getHeight()/5, Color.RED, Color.TRANSPARENT, Shader.TileMode.MIRROR));
                    Path path = new Path();
                    path.reset();
                    path.setFillType(Path.FillType.WINDING);
                    path.moveTo(0,canvas.getHeight());
                    path.quadTo(x+50,canvas.getHeight(),x+plane.getWidth()/3,canvas.getHeight()-y-plane.getHeight()/4);
                    path.lineTo(x+plane.getWidth()/3,canvas.getHeight());
                    path.lineTo(0,canvas.getHeight());
                    path.close();

                    canvas.drawPath(path,paint);
                    canvas.drawText(String.format("x%.2f",c),canvas.getWidth()/2-100, (float) (canvas.getHeight()/2-100),text);
                }
                holder.unlockCanvasAndPost(canvas);
                if(isEnds) {
                    score += 10 *c;
                    score = Math.max(score,0);
                    listener.end();
                    togglePause();
                }
            } catch (Exception ignored) {

            }
        }
    };
    private int tmp = 0;
    PublishSubject<Integer> subject = PublishSubject.create();
    public PublishSubject<Integer> getList() {
        return subject;
    }
    EndListener listener;
    public void setEnd(EndListener listener) {
        this.listener = listener;
    }
    public void togglePause() {
        paused = !paused;
        if(paused) {
            progr = 0;
            x = 0;
            y = 0;
            count = 0;
            c = 0;
            millis = 0;
        } else {
            score -= 10;
            score = Math.max(score,0);
        }
    }

    public static interface EndListener {
        void end();
        void progr();
    }
}
