package com.example.unofinal.backend;

import com.example.unofinal.R;

public class SimpleCard {

    //Includes Color and Number
    private String name;

    //Image
    private int Image;

    public SimpleCard(String name, int image) {
        this.name = name;
        Image = image;
    }

    public SimpleCard(String name){
        this.name = name;

        addImage();
    }

    //TODO: change the name of the cards and wild is currently pickColor?

    public void addImage(){
        if(name.contains("RED")){
            if(name.contains("ONE")){
                setImage(R.drawable.redone);
            }else if(name.contains("TWO")){
                setImage(R.drawable.redtwo);
            }else if(name.contains("THREE")){
                setImage(R.drawable.redthree);
            }else if(name.contains("FOUR")){
                setImage(R.drawable.redfour);
            }else if(name.contains("FIVE")){
                setImage(R.drawable.redfive);
            }else if(name.contains("SIX")){
                setImage(R.drawable.redsix);
            }else if(name.contains("SEVEN")){
                setImage(R.drawable.redseven);
            }else if(name.contains("EIGHT")){
                setImage(R.drawable.redeight);
            }else if(name.contains("NINE")){
                setImage(R.drawable.rednine);
            }else if(name.contains("REVERSE")){
                setImage(R.drawable.redreverse);
            }else if(name.contains("DRAW2")){
                setImage(R.drawable.reddrawtwo);
            }else if(name.contains("SKIP")){
                setImage(R.drawable.redskip);
            }

        }else if(name.contains("BLUE")){
            if(name.contains("ONE")){
                setImage(R.drawable.blueone);
            }else if(name.contains("TWO")){
                setImage(R.drawable.bluetwo);
            }else if(name.contains("THREE")){
                setImage(R.drawable.bluethree);
            }else if(name.contains("FOUR")){
                setImage(R.drawable.bluefour);
            }else if(name.contains("FIVE")){
                setImage(R.drawable.bluefive);
            }else if(name.contains("SIX")){
                setImage(R.drawable.bluesix);
            }else if(name.contains("SEVEN")){
                setImage(R.drawable.blueseven);
            }else if(name.contains("EIGHT")){
                setImage(R.drawable.blueeight);
            }else if(name.contains("NINE")){
                setImage(R.drawable.bluenine);
            }else if(name.contains("REVERSE")){
                setImage(R.drawable.bluereverse);
            }else if(name.contains("DRAW2")){
                setImage(R.drawable.bluedrawtwo);
            }else if(name.contains("SKIP")){
                setImage(R.drawable.blueskip);
            }
        }else if(name.contains("GREEN")){
            if(name.contains("ONE")){
                setImage(R.drawable.greenone);
            }else if(name.contains("TWO")){
                setImage(R.drawable.greentwo);
            }else if(name.contains("THREE")){
                setImage(R.drawable.greenthree);
            }else if(name.contains("FOUR")){
                setImage(R.drawable.greenfour);
            }else if(name.contains("FIVE")){
                setImage(R.drawable.greenfive);
            }else if(name.contains("SIX")){
                setImage(R.drawable.greensix);
            }else if(name.contains("SEVEN")){
                setImage(R.drawable.greenseven);
            }else if(name.contains("EIGHT")){
                setImage(R.drawable.greeneight);
            }else if(name.contains("NINE")){
                setImage(R.drawable.greennine);
            }else if(name.contains("REVERSE")){
                setImage(R.drawable.greenreverse);
            }else if(name.contains("DRAW2")){
                setImage(R.drawable.greendrawtwo);
            }else if(name.contains("SKIP")){
                setImage(R.drawable.greenskip);
            }
        }else if(name.contains("YELLOW")){
            if(name.contains("ONE")){
                setImage(R.drawable.yellowone);
            }else if(name.contains("TWO")){
                setImage(R.drawable.yellowtwo);
            }else if(name.contains("THREE")){
                setImage(R.drawable.yellowthree);
            }else if(name.contains("FOUR")){
                setImage(R.drawable.yellowfour);
            }else if(name.contains("FIVE")){
                setImage(R.drawable.yellowfive);
            }else if(name.contains("SIX")){
                setImage(R.drawable.yellowsix);
            }else if(name.contains("SEVEN")){
                setImage(R.drawable.yellowseven);
            }else if(name.contains("EIGHT")){
                setImage(R.drawable.yelloweight);
            }else if(name.contains("NINE")){
                setImage(R.drawable.yellownine);
            } else if(name.contains("REVERSE")){
                setImage(R.drawable.yellowreverse);
            }else if(name.contains("DRAW2")){
                setImage(R.drawable.yellowdrawtwo);
            }else if(name.contains("SKIP")){
                setImage(R.drawable.yellowskip);
            }

            //Wild and Draw 4
        }else{
            if(name.contains("4")){
                setImage(R.drawable.drawfour);
            }else{
                setImage(R.drawable.wild);
            }

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
