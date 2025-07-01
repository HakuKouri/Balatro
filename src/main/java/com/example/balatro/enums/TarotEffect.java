package com.example.balatro.enums;

import com.example.balatro.classes.Joker;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.Suit;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.models.GameModel;

public enum TarotEffect {

    THE_FOOL {
        @Override
        public void apply(GameModel model) {
            if (canPlay(model)) {
                CardViewController.createCardNode(model.getLastConsumableUsed(), model.getConsumableMap());
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getConsumableMap().size() < model.getRunState().getMaxConsumables();
        }
    },

    THE_MAGICIAN {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3 && !model.getSelectedCards().isEmpty())
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(7));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 2 && !model.getSelectedCards().isEmpty();
        }

    },
    THE_HIGH_PRIESTESS {
        @Override
        public void apply(GameModel model) {
            for (int i = model.getConsumableMap().size(); i < model.getRunState().getMaxConsumables(); i++) {
                CardViewController.createCardNode(model.getAllPlanetList().get(model.getRand().nextInt(model.getAllPlanetList().size())), model.getConsumableMap());
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getConsumableMap().size() <= model.getRunState().getMaxConsumables();
        }

    },
    THE_EMPRESS {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3 && !model.getSelectedCards().isEmpty())
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(1));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 2 && !model.getSelectedCards().isEmpty();
        }
    },
    THE_EMPEROR {
        @Override
        public void apply(GameModel model) {
            for (int i = model.getConsumableMap().size(); i < model.getRunState().getMaxConsumables(); i++) {
                CardViewController.createCardNode(model.getAllTarotList().get(model.getRand().nextInt(model.getAllTarotList().size())), model.getConsumableMap());
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getActiveJokerMap().size() < model.getRunState().getMaxJokers();
        }

    },
    THE_HIEROPHANT {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3 && !model.getSelectedCards().isEmpty())
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(0));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 2 && !model.getSelectedCards().isEmpty();
        }
    },
    THE_LOVERS {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() == 1)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(2));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }

    },
    THE_CHARIOT {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() == 1)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(4));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;

        }
    },
    JUSTICE {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() == 1)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(3));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }

    },
    THE_HERMIT {
        @Override
        public void apply(GameModel model) {
            model.getRunState().addMoney(model.getRunState().getMoney());
        }

        @Override
        public boolean canPlay(GameModel model) {
            return true;
        }
    },
    THE_WHEEL_OF_FORTUNE {
        @Override
        public void apply(GameModel model) {
            //TODO WHEEL OF FORTUNE
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getActiveJokerMap().keySet().stream().anyMatch(j -> ((Joker) j.getCard()).getEdition().getId() < 1);
        }
    },
    STRENGTH {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3 && !model.getSelectedCards().isEmpty())
                for (PlayingCard card : model.getSelectedCards()) {
                    //TODO CARD VALUE IMAGE ANPASSEN
                    card.setValue(card.getValue() + 1);
                    if (card.getValue() == 12)
                        card.setValue(2);
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 2 && !model.getSelectedCards().isEmpty();
        }
    },
    THE_HANGED_MAN {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3 && !model.getSelectedCards().isEmpty())
                for (PlayingCard card : model.getSelectedCards()) {
                    model.getRunState().removeCardFromDeckFull(card);
                    //TODO REMOVE CARDS FROM HOLDING ODER PLAYED
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 2 && !model.getSelectedCards().isEmpty();
        }
    },
    DEATH {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3 && !model.getSelectedCards().isEmpty()) {
                model.getSelectedCards().get(0).setCard(model.getSelectedCards().get(1));
                //TODO IMAGE CHANGE
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 2;
        }
    },
    TEMPERANCE {
        @Override
        public void apply(GameModel model) {
            model.getRunState().addMoney(model.getActiveJokerMap().keySet().stream().mapToInt(i -> (int) i.getCard().getSellValue()).sum());
        }

        @Override
        public boolean canPlay(GameModel model) {
            return true;
        }
    },
    THE_DEVIL {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() == 1)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(6));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },
    THE_TOWER {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() == 1)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setEnhancement(model.getAllEnhancementList().get(5));
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },
    THE_STAR {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setSuit(Suit.DIAMONDS);
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 3 && !model.getSelectedCards().isEmpty();
        }
    },
    THE_MOON {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setSuit(Suit.CLUBS);
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 3 && !model.getSelectedCards().isEmpty();
        }
    },
    THE_SUN {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setSuit(Suit.HEARTS);
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 3 && !model.getSelectedCards().isEmpty();
        }
    },
    JUDGEMENT {
        @Override
        public void apply(GameModel model) {
            if (model.getActiveJokerMap().size() < model.getRunState().getMaxJokers())
                CardViewController.createCardNode(model.getAllJokerList().get(model.getRand().nextInt(model.getAllJokerList().size())), model.getActiveJokerMap());
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getActiveJokerMap().size() < model.getRunState().getMaxJokers();
        }
    },
    THE_WORLD {
        @Override
        public void apply(GameModel model) {
            if (model.getSelectedCards().size() < 3)
                for (PlayingCard card : model.getSelectedCards()) {
                    card.setSuit(Suit.SPADES);
                }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() <= 3 && !model.getSelectedCards().isEmpty();
        }
    };


    public void apply(GameModel model) {}

    public boolean canPlay(GameModel model) {
        return false;
    }
}
