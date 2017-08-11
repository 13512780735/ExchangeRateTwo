package com.likeit.currenciesapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 对话框
 * @author WG
 *
 */
public class DialogUtil {
// private static final String TAG = "IDialogImpl";

/**
 * 多选
 * @param contex
 * @param items  多选词 eg: 项目一，项目二
 * @param areaState  多选词区域状态： true , false
 * @param onMultiChoiceClickListener 多选监听
 * @param onClickListener 确认监听
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showMultiChoice(Context contex, String[] items,
		boolean[] areaState,OnMultiChoiceClickListener onMultiChoiceClickListener, OnClickListener onClickListener, String title, String message) {
	
	AlertDialog mAlertDialog = new AlertDialog.Builder(contex)
	.setTitle(title)
	.setMultiChoiceItems(items, areaState, onMultiChoiceClickListener)
	.setMessage(message)
	.setPositiveButton("确定", onClickListener)
	.setNegativeButton("取消", null).show();
	
	return mAlertDialog;
}

/**
 * 单选
 * @param context
 * @param items
 * @param itemClickListener
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showDialogList(Context context, String[] items,
		OnClickListener itemClickListener, String title, String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setItems(items, itemClickListener)
			.setNegativeButton("取消", null).show();

	return mAlertDialog;
}

/**
 * 单选
 * @param context
 * @param items
 * @param checkedItem
 * @param itemClickListener
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showSingleChoice(Context context, String[] items, int checkedItem,
		OnClickListener itemClickListener, String title, String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setSingleChoiceItems(items, checkedItem, itemClickListener)
			.setNegativeButton("取消", null).show();

	return mAlertDialog;
}

/**
 * 只有确定
 * onClickListener 可放 null
 * eg :
 * DialogUtil.showAlert(mContext, null, "提醒", result);
 * @param context
 * @param onClickListener
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showAlert(Context context,
		OnClickListener onClickListener, String title, String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setMessage(message)
			.setPositiveButton("确定", onClickListener).show();

	return mAlertDialog;
}

/**
 * 有确定与取消
 * @param context
 * @param onClickListener
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showDialog(Context context,
		OnClickListener onClickListener, String title, String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setMessage(message)
			.setPositiveButton("確認", onClickListener)
			.setNegativeButton("取消", null).show();
			mAlertDialog.getButton(mAlertDialog.BUTTON_POSITIVE).setTextSize(18);
			mAlertDialog.getButton(mAlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
			mAlertDialog.getButton(mAlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
			mAlertDialog.getButton(mAlertDialog.BUTTON_NEGATIVE).setTextSize(18);

	return mAlertDialog;
}

/**
 * 显示 View 
 * 可以是 editText 等View,但是图片下面有其它方法
 * @param context
 * @param onClickListener
 * @param view
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showView(Context context,
		OnClickListener onClickListener, View view, String title,
		String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setMessage(message).setView(view)
			.setPositiveButton("确定", onClickListener)
			.setNegativeButton("取消", null).show();

	return mAlertDialog;
}

/**
 * 2个确认 1 个取消 eg
 * @param context
 * @param onClickListener1
 * @param buttonTitle1
 * @param onClickListener2
 * @param buttonTitle2
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showDoubleDialog(Context context,
		OnClickListener onClickListener1, String buttonTitle1,
		OnClickListener onClickListener2, String buttonTitle2,
		String title, String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setMessage(message)
			.setPositiveButton(buttonTitle1, onClickListener1)
			.setPositiveButton(buttonTitle2, onClickListener2)
			.setNegativeButton("取消", null).show();

	return mAlertDialog;
}

/**
 * 2个确认 1 个取消 eg
 * @param context
 * @param onClickListener1
 * @param buttonTitle1
 * @param title
 * @param message
 * @return
 */
public static AlertDialog showDoubleDialog(Context context,
		OnClickListener onClickListener1, String buttonTitle1,
		String title, String message) {

	AlertDialog mAlertDialog = new AlertDialog.Builder(context)
			.setTitle(title).setMessage(message)
			.setPositiveButton(buttonTitle1, onClickListener1)
			.setNegativeButton("取消", null).show();

	return mAlertDialog;
}

/**
 * 全屏显示图片
 * @param context
 * @return
 */
public static Dialog showViewFullScreen(Context context, int imageID) {

	Dialog mDialog = new Dialog(context);
	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	mDialog.getWindow().setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	mDialog.setContentView(imageID);
	mDialog.show();
	return mDialog;
}

/**
 * 全屏显示图片
 * @param context
 * @param view
 * @return
 * 
 * 使用方法
 * 	ImageView iv = new ImageView(mContext);
	iv.setImageBitmap((Bitmap) pictures.get(id));
	DialogUtil.showViewFullScreen(mContext, (iv));
 */
public static Dialog showViewFullScreen(Context context, View view) {

	Dialog mDialog = new Dialog(context);
	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	mDialog.getWindow().setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	mDialog.setContentView(view);
	mDialog.show();
	return mDialog;
}
}
