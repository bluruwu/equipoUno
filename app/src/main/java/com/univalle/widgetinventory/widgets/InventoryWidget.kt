package com.univalle.widgetinventory.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.view.LoginActivity


class InventoryWidget : AppWidgetProvider() {
    private lateinit var sharedPreferences: SharedPreferences
    private val eyePreferences = "eyePreferences"
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    private fun isUserLogged(context: Context): Boolean {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        return !email.isNullOrEmpty()
    }

    private fun getEyeState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(eyePreferences, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("eye_state", false)
    }

    private fun saveEyeState(context: Context, isOpen: Boolean) {
        val sharedPreferences = context.getSharedPreferences(eyePreferences, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean("eye_state", isOpen)
        editor?.apply()
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d("Alerta", "onrecieve")
        Log.d("Alerta", "ESte es_ "+ intent?.action)
        if(intent?.action == "TOGGLE_EYE"){
            val eyeState = context?.let { getEyeState(it) }
            Log.d("Alerta", "leo accion")
            if(eyeState == true) {
                Log.d("Alerta", "Estaba encendido")
                if(isUserLogged(context)){
                    Log.d("Alerta", "estoy logueado")
                    saveEyeState(context, false)
                    AppWidgetManager.getInstance(context).let { appWidgetManager ->
                        val thisWidget = ComponentName(context, InventoryWidget::class.java)
                        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                        for (appWidgetId in appWidgetIds) {
                            updateAppWidget(context, appWidgetManager, appWidgetId)
                        }
                    }
                } else{
                    val login = Intent(context, LoginActivity::class.java).apply{
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("Redirect", true )
                    }
                    Log.d("Alerta", "no estoy logueado")
                }
            } else {
                Log.d("Alerta", "Estaba apagado")
                saveEyeState(context!!, true)
                AppWidgetManager.getInstance(context).let { appWidgetManager ->
                    val thisWidget = ComponentName(context, InventoryWidget::class.java)
                    val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                    for (appWidgetId in appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId)
                    }
                }
            }
        }else if (intent?.action == "OPEN_INVENTORY"){
            Log.d("Alerta", "soy open")
        } else {
            Log.d("Alerta", "Ahh")
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    Log.d("Alerta", "HOLAAAAAAA")
    val widget = context.getSharedPreferences("eyePreferences", Context.MODE_PRIVATE)
    val eyeState = widget.getBoolean("eye_state", false)
    val changeImage = if (eyeState) R.drawable.eye else R.drawable.noeye
    val views = RemoteViews(context.packageName, R.layout.inventory_widget)
    views.setImageViewResource(R.id.eyeToggle, changeImage)
    val intentEye = Intent(context, InventoryWidget::class.java)
    intentEye.action = "TOGGLE_EYE"

    val pendingIntentEye = PendingIntent.getBroadcast(
        context,
        0,
        intentEye,
        PendingIntent.FLAG_MUTABLE
    )
    views.setOnClickPendingIntent(R.id.eyeToggle, pendingIntentEye)

    val intentOpenInv = Intent(context, InventoryWidget::class.java)
    intentOpenInv.action = "OPEN_INVENTORY"

    val pendingIntentOpenInv = PendingIntent.getBroadcast(
        context,
        1,
        intentOpenInv,
        PendingIntent.FLAG_MUTABLE
    )
    views.apply {
        setOnClickPendingIntent(R.id.config, pendingIntentOpenInv)
    }
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
