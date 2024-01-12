package com.neelkanth.zerokanta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.neelkanth.zerokanta.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    // 0 - 0
    // 1 - X
    int activePlayer = 1, current = 0;
    /* Game State
     * 0 - O
     * 1 - X
     * 2 = null
     */
    int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    boolean gameActive = true;
    int [][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                             {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                             {0, 4, 8}, {2, 4, 6}};

    ImageView [] imgs = new ImageView[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.resetImg.setOnClickListener(v -> resetGame());

        imgs[0] = findViewById(R.id.img1);
        imgs[1] = findViewById(R.id.img2);
        imgs[2] = findViewById(R.id.img3);
        imgs[3] = findViewById(R.id.img4);
        imgs[4] = findViewById(R.id.img5);
        imgs[5] = findViewById(R.id.img6);
        imgs[6] = findViewById(R.id.img7);
        imgs[7] = findViewById(R.id.img8);
        imgs[8] = findViewById(R.id.img9);


    }

    // Getting view when image view is pressed
    public void playerTap(View view){

        ImageView img = (ImageView) view;
        int tappedImg = Integer.parseInt(img.getTag().toString());

        if (!gameActive){
            resetGame();
        }

        // Empty Grid cell
        if (gameState[tappedImg] == 2 && gameActive){
            // Player x's turn
            gameState[tappedImg] = activePlayer;
            // Image view is translated in z position by -1000
            img.setTranslationX(-1000f);
            img.setAlpha(0f);
            if (activePlayer == 1){
                img.setImageResource(R.drawable.x);
                activePlayer = 0;
                binding.statusImg.setImageResource(R.drawable.player_two);
            }
            else if (activePlayer == 0){
                // Player O's turn
                img.setImageResource(R.drawable.o);
                activePlayer = 1;
                binding.statusImg.setImageResource(R.drawable.player_one);
            }
            current++;
            // Image view is translated in z position by 1000 in 300ms
            img.animate().alpha(1f).setDuration(300);
            img.animate().translationXBy(1000f).setDuration(200);
        }
        // Check game if game is completed
        checkGame();

    }

    public void checkGame(){

        // Iterate 2D array in 1D ([9 row][3 col] = [3col] iterate 9 times)
        for (int[] winPosition : winPositions){

            // EX. grid cell (0, 1, 2) == gameState value 1(X player) and grid cell (2) != 2(nul)
            // Here gameState represent grid cells.
            if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[2]] != 2){
                // Someone has won
                // Checking who is the winner
                gameActive = false;

                // Highlight the winning cells
                imgs[winPosition[0]].setBackgroundResource(R.drawable.highlight_background);
                imgs[winPosition[1]].setBackgroundResource(R.drawable.highlight_background);
                imgs[winPosition[2]].setBackgroundResource(R.drawable.highlight_background);


                if (gameState[winPosition[0]] == 1){
                    // X has won the game
                    binding.statusImg.setImageResource(R.drawable.player_one_won);

                    // Removing other image view

                    Toast.makeText(this, "Player 1, Tap again to play!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // O has won the game
                    binding.statusImg.setImageResource(R.drawable.player_two_won);
                    Toast.makeText(this, "Player 1, Tap again to play!", Toast.LENGTH_SHORT).show();
                }
            }
            else if (current > 8){
                // Game Draw
                gameActive = false;
                binding.statusImg.setImageResource(R.drawable.game_draw);
                Toast.makeText(this, "Player 1, Tap again to play!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void resetGame(){
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setImageResource(0);
            imgs[i].setBackgroundResource(0);
        }
        binding.statusImg.setImageResource(R.drawable.player_one);
        gameActive = true;
        current = 0;
        activePlayer = 1;
        // Fill the all array element with 2
        Arrays.fill(gameState, 2);
    }

}