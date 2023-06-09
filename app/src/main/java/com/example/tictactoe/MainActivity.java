package com.example.tictactoe;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignación de elementos de texto a las variables correspondientes
        textViewPlayer1 = findViewById(R.id.text_view_Player_1);
        textViewPlayer2 = findViewById(R.id.text_view_Player_2);


        // Configuración de los botones del juego y asignación de OnClickListener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        // Configuración del botón de reset y asignación de OnClickListener
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }


    // El método onClick() se ejecuta cuando se hace clic en uno de los botones del juego.
    @Override
    public void onClick(View v) {

        // Verifica si el botón ya tiene texto. Si es así, no se hace nada.
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        // Coloca la marca correspondiente al jugador actual en el botón.
        // Si es el turno del jugador 1, se coloca "X", de lo contrario, se coloca "O".
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++; // Incrementa el contador de rondas.

        // Verifica si hay una victoria después de cada jugada.
        if (checkForWin()) {
            // Si hay victoria, se determina qué jugador ganó y se llama al método correspondiente.
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }

        } else if (roundCount == 9) {
            // Si no hay victoria y se han jugado 9 rondas, se declara un empate.
            draw();
        } else {
            // Si no hay victoria ni empate, se cambia el turno al siguiente jugador.
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3]; // Matriz para almacenar los textos de los botones


        // Obtiene los textos de los botones y los guarda en la matriz 'field'
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Verifica las filas para determinar si hay una victoria
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        // Verifica las columnas para determinar si hay una victoria
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        // Verifica la diagonal principal para determinar si hay una victoria
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        // Verifica la diagonal secundaria para determinar si hay una victoria
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false; // No hay una victoria
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }
}
