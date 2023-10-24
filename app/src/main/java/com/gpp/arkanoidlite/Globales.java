package com.gpp.arkanoidlite;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.gpp.arkanoidlite.models.Brick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Globales {

    public static int level;
    public static  List<Brick> mapaInicial;


    public static void readAndExecuteLevel(Context ctx, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d("ERROR", "SQL script file name is empty");
            return;
        }

        AssetManager assetManager = ctx.getAssets();
        BufferedReader reader = null;

        try {
            InputStream is = assetManager.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
            executeCreateLevel( reader);
        } catch (IOException e) {
            Log.e("ERROR", "IOException:", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("ERROR", "IOException:", e);
                }
            }
        }

    }


    private static void executeCreateLevel(BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        Globales.mapaInicial = new ArrayList<Brick>();

        while ((line = reader.readLine()) != null) {
            String[] bloque = line.split(",");
            Globales.mapaInicial.add(new Brick(Float.parseFloat(bloque[0]),Float.parseFloat(bloque[1]),null, Integer.parseInt(bloque[2]), 2, 0));
        }
    }

}
