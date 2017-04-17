package com.xjf.repository.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/11/24--11:45
 * Function:
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
//===========================================================================// 
//                                                                           // 
//                            _ooOoo_                                        // 
//                           o8888888o                                       // 
//                           88" . "88                                       // 
//                           (| -_- |)                                       // 
//                            O\ = /O                                        // 
//                        ____/`---'\____                                    // 
//                      .   ' \\| |// `.                                     // 
//                       / \\||| : |||// \                                   // 
//                     / _||||| -:- |||||- \                                 // 
//                       | | \\\ - /// | |                                   // 
//                     | \_| ''\---/'' | |                                   // 
//                      \ .-\__ `-` ___/-. /                                 // 
//                   ___`. .' /--.--\ `. . __                                // 
//                ."" '< `.___\_<|>_/___.' >'"".                             // 
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |                          // 
//                 \ \ `-. \_ __\ /__ _/ .-` / /                             // 
//         ======`-.____`-.___\_____/___.-`____.-'======                     // 
//                            `=---='                                        // 
//                                                                           // 
//         .............................................                     // 
//                  佛祖镇楼                  BUG辟易                        //       
//          佛曰:                                                            //   
//                  写字楼里写字间，写字间里程序员；                         //                 
//                  程序人员写程序，又拿程序换酒钱。                         //                 
//                  酒醒只在网上坐，酒醉还来网下眠；                         //                 
//                  酒醉酒醒日复日，网上网下年复年。                         //                 
//                  但愿老死电脑间，不愿鞠躬老板前；                         //                 
//                  奔驰宝马贵者趣，公交自行程序员。                         //                 
//                  别人笑我忒疯癫，我笑自己命太贱；                         //                 
//                  不见满街漂亮妹，哪个归得程序员？                         //                 
//===========================================================================// 