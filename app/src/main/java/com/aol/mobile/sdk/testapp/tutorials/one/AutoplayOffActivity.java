package com.aol.mobile.sdk.testapp.tutorials.one;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aol.mobile.sdk.player.OneSDK;
import com.aol.mobile.sdk.player.OneSDKBuilder;
import com.aol.mobile.sdk.player.Player;
import com.aol.mobile.sdk.player.view.PlayerFragment;
import com.aol.mobile.sdk.testapp.Data;
import com.aol.mobile.sdk.testapp.R;

public class AutoplayOffActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_fragment);

        FragmentManager fm = getFragmentManager();
        final PlayerFragment playerFragment = (PlayerFragment) fm.findFragmentById(R.id.player_fragment);

        new OneSDKBuilder(getApplicationContext())
                .create(new OneSDKBuilder.Callback() {
                    @Override
                    public void onSuccess(@NonNull OneSDK oneSDK) {
                        useSDK(oneSDK, playerFragment);
                    }

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Creating SDK", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void useSDK(@NonNull OneSDK oneSDK, @NonNull final PlayerFragment playerFragment) {
        oneSDK.createBuilder()
                .setAutoplay(false)
                .buildForVideo(Data.VIDEO_ID, new Player.Callback() {
                    @Override
                    public void success(@NonNull Player player) {
                        playerFragment.getBinder().setPlayer(player);
                    }

                    @Override
                    public void error(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
