package com.luckyaf.oneforall.module.test.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/9
 */
@SuppressWarnings("unused")
public class SketchView extends View implements View.OnTouchListener {
    private static final String TAG = SketchView.class.getName();
    public static final int STROKE = 0;//画笔
    public static final int ERASER = 1;//橡皮
    public static final int DEFAULT_STROKE_SIZE = 7;//画笔默认大小
    public static final int DEFAULT_ERASER_SIZE = 50;//橡皮默认大小

    private float strokeSize = DEFAULT_STROKE_SIZE;//画笔大小
    private int strokeColor = Color.BLACK;//画笔颜色
    private float eraserSize = DEFAULT_ERASER_SIZE;//橡皮大小
    private int eraserColor = Color.WHITE;//橡皮颜色

    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private int width, height;
    //绘画步骤
    private ArrayList<Pair<Path, Paint>> paths = new ArrayList<>();
    private ArrayList<Pair<Path, Paint>> undonePaths = new ArrayList<>();//撤销绘画步骤

    private Bitmap bitmap;//画布背景
    private int mode = STROKE;//当前绘画模式 默认在用画笔


    private OnDrawChangedListener onDrawChangedListener;//绘制完成一个步骤后的监听

    public SketchView(Context context, AttributeSet attr) {
        super(context, attr);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(Color.WHITE);//设置背景色为白色
        setOnTouchListener(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//高频
        mPaint.setColor(strokeColor);//
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeSize);
        mPath = new Path();
        invalidate();//请求重绘View树，即draw()过程
    }

    /**
     * 设置绘图模式  使用画笔还是橡皮擦
     * @param mode  模式
     */
    public void setMode(int mode) {
        if (mode == STROKE || mode == ERASER) {
            this.mode = mode;
        }
    }

    /**
     * 获取当前模式
     * @return  模式
     */
    public int getMode() {
        return this.mode;
    }

    /**
     * 设置画布背景
     *
     * @param bitmap   背景图
     */
    public void setBackgroundBitmap(Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
            // set default bitmap config if none
            if (bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
        }
        this.bitmap = bitmap;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 触摸事件
     * @param arg0    触摸的视图
     * @param event   事件
     * @return        是否结束传递
     */
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE://移动
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP://抬起
                touchUp();
                invalidate();
                break;
            default:
                Log.e(TAG, "Wrong element: " + event.getAction());
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap != null) {//如果有背景图
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        for (Pair<Path, Paint> p : paths) {
            canvas.drawPath(p.first, p.second);
        }

        onDrawChangedListener.onDrawChanged();
    }

    /**
     * 手指按下
     * @param x  坐标X
     * @param y  坐标Y
     */
    private void touchStart(float x, float y) {
        // Clearing undone list
        undonePaths.clear();

        //设置画笔
        if (mode == ERASER) {
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(eraserSize);
        } else {
            mPaint.setColor(strokeColor);
            mPaint.setStrokeWidth(strokeSize);
        }

        // Avoids that a sketch with just erasures is saved
        //如果图上 没有数据也没有背景图  使用橡皮擦 则不用渲染
        if (!(paths.size() == 0 && mode == ERASER && bitmap == null)) {
            paths.add(new Pair<>(mPath, new Paint(mPaint)));
        }

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    /**
     * 触摸点移动 即画笔或橡皮擦移动
     * @param x 坐标X
     * @param y 做标Y
     */
    private void touchMove(float x, float y) {
        mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
        mX = x;
        mY = y;
    }

    /**
     * 抬起手指
     */
    private void touchUp() {
        mPath.lineTo(mX, mY);
        // Avoids that a sketch with just erasures is saved
        if (!(paths.size() == 0 && mode == ERASER && bitmap == null)) {
            paths.add(new Pair<>(mPath, new Paint(mPaint)));
        }
        // kill this so we don't double draw
        mPath = new Path();
    }


    /**
     * 返回画完后的图片
     *
     * @return 图片
     */
    public Bitmap getBitmap() {
        if (paths.size() == 0) {
            return null;
        }
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(eraserColor);
        }
        Canvas canvas = new Canvas(bitmap);
        for (Pair<Path, Paint> p : paths) {
            canvas.drawPath(p.first, p.second);
        }
        return bitmap;
    }

    /**
     * 撤销
     */
    public void undo() {
        if (paths.size() >= 2) {
            undonePaths.add(paths.remove(paths.size() - 1));
            // If there is not only one path remained both touch and move paths are removed
            undonePaths.add(paths.remove(paths.size() - 1));
            invalidate();
        }
    }

    /**
     * 撤销 撤销
     */
    public void redo() {
        if (undonePaths.size() > 0) {
            paths.add(undonePaths.remove(undonePaths.size() - 1));
            paths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
    }

    /**
     * 获取还能撤销几次
     * @return  撤销数量  也是已经描绘的段数
     */
    public int getUndoneCount() {
        return undonePaths.size();
    }


    public ArrayList<Pair<Path, Paint>> getPaths() {
        return paths;
    }


    public void setPaths(ArrayList<Pair<Path, Paint>> paths) {
        this.paths = paths;
    }


    public ArrayList<Pair<Path, Paint>> getUndonePaths() {
        return undonePaths;
    }


    public void setUndonePaths(ArrayList<Pair<Path, Paint>> undonePaths) {
        this.undonePaths = undonePaths;
    }


    public int getStrokeSize() {
        return Math.round(this.strokeSize);
    }


    /**
     * 设置橡皮擦和画笔的大小
     * @param size   大小
     * @param eraserOrStroke   橡皮擦还是画笔
     */
    public void setSize(int size, int eraserOrStroke) {
        switch (eraserOrStroke) {
            case STROKE:
                strokeSize = size;
                break;
            case ERASER:
                eraserSize = size;
                break;
            default:
                Log.e(TAG, "Wrong element : " + eraserOrStroke);
        }

    }


    public int getStrokeColor() {
        return this.strokeColor;
    }


    public void setStrokeColor(int color) {
        strokeColor = color;
    }


    public int getEraserColor(){return this.eraserColor;}

    public void setEraserColor(int color){eraserColor = color;}

    /**
     * 全部清除
     */
    public void erase() {
        paths.clear();
        undonePaths.clear();
        invalidate();
    }


    public void setOnDrawChangedListener(OnDrawChangedListener listener) {
        this.onDrawChangedListener = listener;
    }

    public interface OnDrawChangedListener{

        void onDrawChanged();
    }
}