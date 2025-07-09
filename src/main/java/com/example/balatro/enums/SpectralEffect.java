package com.example.balatro.enums;

import com.example.balatro.domain.card.Card;
import com.example.balatro.domain.card.Joker;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.models.GameModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum SpectralEffect {

    FAMILIAR {
        @Override
        public void apply(GameModel model) {
            if(canPlay(model))
                //Destroy 1 Random -> 3 Random Face Cards
                System.out.println("Spectral Effect FAMILIAR");
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },

    GRIM {
        @Override
        public void apply(GameModel model) {
            //Destroy 1 Random -> 2 Aces
            System.out.println("Spectral Effect GRIM");
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },
    INCANTATION {
        @Override
        public void apply(GameModel model) {
            //Destroy 1 Random -> 4 Numbers
            System.out.println("Spectral Effect INCANTATION");
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },
    TALISMAN {
        @Override
        public void apply(GameModel model) {
            //1 Card gains Gold Seal
            System.out.println("Spectral Effect TALISMAN");
            if(canPlay(model))
                model.getSelectedCards().getFirst().setSeal(model.getAllSealList().get(0));
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },
    AURA {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect AURA");
            if (canPlay(model)) {
                if(model.isBoosterOpeningVisibility()) {
                    model.getBoosterDrawModel().getPlayingCardsDrawn().get(model.getRand().nextInt(model.getBoosterDrawModel().getPlayingCardsDrawn().size())).setEdition(model.getRandomEdition());
                } else if(!model.getHandCardViewManager().getViewMap().isEmpty()) {
                    model.getHandCardViewManager().getCardList().get(model.getRand().nextInt(model.getHandCardViewManager().size())).setEdition(model.getRandomEdition());
                } else {
                    System.out.println("Spectral Effect AURA not Possible");
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },
    WRAITH {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect WRAITH");
            if(canPlay(model)) {
                List<Joker> rareList = model.getAllJokerList().stream().filter(joker -> "Rare".equals(joker.getRarity())).toList();
                model.getRunState().setMoney(0);
                model.getJokerManager().create(rareList.get(model.getRand().nextInt(rareList.size())));
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().size() < model.getRunState().getMaxJokers();
        }
    },
    SIGIL {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect SIGIL");
            if (canPlay(model)) {
                int suitIndex = model.getRand().nextInt(4);
                if(model.isBoosterOpeningVisibility()) {
                    model.getBoosterDrawModel().getPlayingCardsDrawn().forEach(card -> card.setSuitIndex(suitIndex));
                } else if(!model.getHandCardViewManager().getViewMap().isEmpty()) {
                    model.getHandCardViewManager().getCardList().forEach(card -> ((PlayingCard)card).setSuitIndex(suitIndex));
                } else {
                    System.out.println("Spectral Effect SIGIL not Possible");
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },
    OUIJA {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect OUIJA");
            if (canPlay(model)) {
                int rankIndex = model.getRand().nextInt(13);
                if(model.isBoosterOpeningVisibility()) {
                    model.getBoosterDrawModel().getPlayingCardsDrawn().forEach(card -> card.setRankIndex(rankIndex));
                } else if(!model.getHandCardViewManager().getViewMap().isEmpty()) {
                    model.getHandCardViewManager().getCardList().forEach(card -> ((PlayingCard)card).setRankIndex(rankIndex));
                } else {
                    System.out.println("Spectral Effect OUIJA not Possible");
                }
                model.getRunState().decreaseHandSizeBy(1);
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },
    ECTOPLASM {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect ECTOPLASM");
            if (canPlay(model)) {
                model.getJokerManager().getCardList().get(model.getRand().nextInt(model.getJokerManager().size())).setEdition(model.getAllEditionList().get(4));
                model.getRunState().decreaseHandSizeBy(1 + model.getRunState().getUsedEctoplasm());
                model.getRunState().incrementUsedEctoplasm();
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().size() > 0;
        }
    },
    IMMOLATE {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect IMMOLATE");
            if (canPlay(model)) {
                List<? extends Card> cardList = model.isBoosterOpeningVisibility() ? model.getBoosterDrawModel().getPlayingCardsDrawn().stream().toList() : !model.getHandCardViewManager().getViewMap().isEmpty() ? model.getHandCardViewManager().getCardList() : null;
                if(cardList != null) {
                    Set<Card> cardSet = new HashSet<>();
                    while(cardSet.size() < cardList.size() && cardSet.size() < 5) {
                        cardSet.add(cardList.get(model.getRand().nextInt(cardList.size())));
                    }
                    cardSet.forEach(card -> {
                        if(model.isBoosterOpeningVisibility()) {
                            model.getBoosterDrawModel().getPlayingCardsDrawn().remove(card);
                        } else model.getHandCardViewManager().getCardList().remove(card);
                    });
                } else {
                    System.out.println("Spectral Effect IMMOLATE not Possible");
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return (!model.getHandCardViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility()) && model.getRunState().getMaxHandSize() > 1;

        }
    },
    ANKH {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },
    DEJA_VU {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },

    HEX {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },

    TRANCE {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },

    MEDIUM {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },

    CRYPTID {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },

    THE_SOUL {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    },

    BLACK_HOLE {
        @Override
        public void apply(GameModel model) {

        }

        @Override
        public boolean canPlay(GameModel model) {
            return false;
        }
    };

    public void apply(GameModel model) {}

    public boolean canPlay(GameModel model) {
        return false;
    }

}
