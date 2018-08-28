package com.shareefoo.pubgcompanion.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.activities.MainActivity;
import com.shareefoo.pubgcompanion.data.SpManager;
import com.shareefoo.pubgcompanion.provider.MatchContract;

/**
 * Implementation of App Widget functionality.
 */
public class PUBGAppWidget extends AppWidgetProvider {

    public static final String ACTION_APPWIDGET = "ACTION_APPWIDGET";

    private static int games = 0;
    private static int wins = 0;
    private static int top10 = 0;

    private static SpManager spManager;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pubgapp_widget);

        spManager = SpManager.getInstance(context);

        games = spManager.getInt("no_games", 0);
        wins = spManager.getInt("no_wins", 0);
        top10 = spManager.getInt("no_top10", 0);

        views.setTextViewText(R.id.textView_games, String.valueOf(games));
        views.setTextViewText(R.id.textView_wins, String.valueOf(wins));
        views.setTextViewText(R.id.textView_top10, String.valueOf(top10));

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.layout_container, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        Log.d("DDD", "onReceive: ");
//
//        if (intent.getAction().equals(ACTION_APPWIDGET)) {
//            // Construct the RemoteViews object
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pubgapp_widget);
//
//            views.setTextViewText(R.id.textView_games, intent.getStringExtra("games"));
//            views.setTextViewText(R.id.textView_wins, intent.getStringExtra("wins"));
//            views.setTextViewText(R.id.textView_top10, intent.getStringExtra("top10"));
//
//            ComponentName appWidget = new ComponentName(context, PUBGAppWidget.class);
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//
//            // Instruct the widget manager to update the widget
//            appWidgetManager.updateAppWidget(appWidget, views);
//        }
//
//    }

}

