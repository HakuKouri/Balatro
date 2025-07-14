package com.example.balatro.enums;

import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;

public enum PlanetEffect {
    PLUTO {
        @Override
        public void apply(GameModel model) {

        }
    },
    MERCURY {
        @Override
        public void apply(GameModel model) {
        }
    },
    URANUS {
        @Override
        public void apply(GameModel model) {
        }
    },
    VENUS {
        @Override
        public void apply(GameModel model) {
        }
    },

    SATURN {
        @Override
        public void apply(GameModel model) {
        }
    },

    JUPITER {
        @Override
        public void apply(GameModel model) {
        }
    },

    EARTH {
        @Override
        public void apply(GameModel model) {
        }
    },

    MARS {
        @Override
        public void apply(GameModel model) {
        }
    },

    NEPTUNE {
        @Override
        public void apply(GameModel model) {
        }
    },

    PLANET_X {
        @Override
        public void apply(GameModel model) {
        }
    },

    CERES {
        @Override
        public void apply(GameModel model) {
        }
    },

    ERIS {
        @Override
        public void apply(GameModel model) {
        }
    };

    public void apply(GameModel model) {}

}
