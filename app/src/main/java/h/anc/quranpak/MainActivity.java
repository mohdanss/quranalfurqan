package h.anc.quranpak;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText curr_surah_number;
    EditText curr_ayah_number;

    TextView ayat_result;
    TextView ayah_number;
    TextView surah_name;

    Button search_button;
    Button next_ayah_button;
    Button prev_ayah_button;

    String surah_name_str;
    String ayah_number_str;
    String curr_ayah_str;

    Integer curr_surah_num_int;
    Integer curr_ayah_num_int;
    Integer curr_surah_max_ayahs;
    Integer surah_start_ayah_int;

    void init() {
        curr_surah_number = findViewById(R.id.surah_num_ip);
        curr_ayah_number = findViewById(R.id.ayah_number_ip);

        ayat_result = findViewById(R.id.ayat_result_text);
        ayah_number = findViewById(R.id.ayah_number_text);
        surah_name = findViewById(R.id.surah_name_text);

        search_button = findViewById(R.id.search_btn);
        next_ayah_button = findViewById(R.id.next_btn);
        prev_ayah_button = findViewById(R.id.prev_btn);

        surah_name_str = "سورۃ: ";
        ayah_number_str = "آیۃ نمبر: ";
        curr_ayah_str = "Select Surah and Ayah Number";

        curr_surah_num_int = 0;
        curr_ayah_num_int = 0;
        curr_surah_max_ayahs = 0;
        surah_start_ayah_int = 0;


        ayah_number.setText(ayah_number_str);
        surah_name.setText(surah_name_str);
        ayat_result.setText(curr_ayah_str);

        ayat_result.setMovementMethod(new ScrollingMovementMethod());
    }

    Boolean isValidVerse() {
        curr_surah_max_ayahs = new Helper().surahAyatCount[curr_surah_num_int - 1];
        if (curr_ayah_num_int > curr_surah_max_ayahs || curr_ayah_num_int < 1)
            return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the variables
        init();

        curr_ayah_number.setEnabled(false);

        prev_ayah_button.setEnabled(false);
        next_ayah_button.setEnabled(false);
        curr_ayah_number.setTextColor(getResources().getColor(R.color.dark_golden));
        curr_ayah_number.setHint("Choose Surah First");
        search_button.setEnabled(false);

        // input surah text change listener
        curr_surah_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!curr_surah_number.getText().toString().equals("")) {
                    curr_surah_num_int = Integer.parseInt(curr_surah_number.getText().toString());
                    if (curr_surah_num_int > 114 || curr_surah_num_int < 1 && isValidVerse()) {
                        curr_ayah_number.setHint("Invalid Surah Number");
                        curr_ayah_number.setEnabled(false);
                        surah_name.setText("سورۃ: ");
                        ayah_number.setText("آیۃ نمبر: ");
                        ayat_result.setText("Invalid Surah Number");

                        prev_ayah_button.setEnabled(false);
                        next_ayah_button.setEnabled(false);
                    } else {
                        curr_surah_max_ayahs = new Helper().surahAyatCount[curr_surah_num_int - 1];
                        curr_surah_num_int = Integer.parseInt(curr_surah_number.getText().toString());

                        curr_ayah_number.setEnabled(true);
                        curr_ayah_number.setTextColor(getResources().getColor(R.color.golden));
                        curr_ayah_number.setHint("Ayah number (1 - " + curr_surah_max_ayahs + ")");

                        surah_name.setText(surah_name_str + new Helper().urduSurahNames[curr_surah_num_int - 1]);
                        surah_start_ayah_int = new Helper().startAyahEachSurah[curr_surah_num_int - 1];

                        curr_ayah_number.setText("");
                        curr_ayah_num_int = 0;
                        curr_ayah_str = "Select Ayah Number";
                        ayah_number.setText(ayah_number_str);

                        ayat_result.setText(new Helper().QuranArabicText[surah_start_ayah_int]);
                    }
                } else {
                    curr_ayah_number.setEnabled(false);
                    curr_ayah_number.setTextColor(getResources().getColor(R.color.dark_golden));
                    curr_ayah_number.setHint("Choose Surah First");
                    surah_name.setText(surah_name_str);
                    ayat_result.setText(curr_ayah_str);

                    prev_ayah_button.setEnabled(false);
                    next_ayah_button.setEnabled(false);

                    ayat_result.setText("Choose Surah First");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // input ayah text change listener
        curr_ayah_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!curr_ayah_number.getText().toString().equals("")) {
                    curr_ayah_num_int = Integer.parseInt(curr_ayah_number.getText().toString());
                    if (isValidVerse()) {
//                        search_button.setEnabled(true);
                        prev_ayah_button.setEnabled(true);
                        next_ayah_button.setEnabled(true);

                        curr_ayah_num_int = Integer.parseInt(curr_ayah_number.getText().toString());
                        ayah_number.setText(ayah_number_str + curr_ayah_num_int);
                        surah_name.setText(surah_name_str + new Helper().urduSurahNames[curr_surah_num_int - 1]);

                        // surah start
                        curr_ayah_str = new Helper().QuranArabicText[surah_start_ayah_int + curr_ayah_num_int - 1];
                        ayat_result.setText(curr_ayah_str);

                    } else {
                        prev_ayah_button.setEnabled(false);
                        next_ayah_button.setEnabled(false);
                        ayat_result.setText("Invalid Ayah Number");
                        ayah_number.setText(ayah_number_str);
                    }
                } else {

                    prev_ayah_button.setEnabled(false);
                    next_ayah_button.setEnabled(false);
                    ayat_result.setText("Choose Ayah First");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // prev ayah button click listener
        prev_ayah_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curr_ayah_num_int > 1) {
                    curr_ayah_num_int--;
                    curr_ayah_number.setText(String.valueOf(curr_ayah_num_int));

                    curr_ayah_str = new Helper().QuranArabicText[surah_start_ayah_int + curr_ayah_num_int - 1];
                    ayat_result.setText(curr_ayah_str);
                } else {
                    Toast.makeText(MainActivity.this, "This is the first ayah of this surah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // next ayah button click listener
        next_ayah_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curr_ayah_num_int < curr_surah_max_ayahs) {
                    curr_ayah_num_int++;
                    curr_ayah_number.setText(String.valueOf(curr_ayah_num_int));

                    curr_ayah_str = new Helper().QuranArabicText[surah_start_ayah_int + curr_ayah_num_int - 1];
                    ayat_result.setText(curr_ayah_str);
                } else {
                    Toast.makeText(MainActivity.this, "This is the last ayah of this surah", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}