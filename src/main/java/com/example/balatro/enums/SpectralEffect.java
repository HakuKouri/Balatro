package com.example.balatro.enums;

import com.example.balatro.domain.card.Card;
import com.example.balatro.domain.card.Edition;
import com.example.balatro.domain.card.Integer;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.models.GameModel;

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
            return !model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
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
            return !model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
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
            return !model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
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
                } else if(!model.getHoldingHandViewManager().getViewMap().isEmpty()) {
                    model.getHoldingHandViewManager().getCardList().get(model.getRand().nextInt(model.getHoldingHandViewManager().getSize())).setEdition(model.getRandomEdition());
                } else {
                    System.out.println("Spectral Effect AURA not Possible");
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },

    WRAITH {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect WRAITH");
            if(canPlay(model)) {
                List<Integer> rareList = model.getAllJokerList().stream().filter(joker -> "Rare".equals(joker.getRarity())).toList();
                model.getRunState().setMoney(0);
                model.getJokerManager().create(rareList.get(model.getRand().nextInt(rareList.size())));
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().getSize() < model.getRunState().getMaxJokers();
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
                } else if(!model.getHoldingHandViewManager().getViewMap().isEmpty()) {
                    model.getHoldingHandViewManager().getCardList().forEach(card -> ((PlayingCard)card).setSuitIndex(suitIndex));
                } else {
                    System.out.println("Spectral Effect SIGIL not Possible");
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
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
                } else if(!model.getHoldingHandViewManager().getViewMap().isEmpty()) {
                    model.getHoldingHandViewManager().getCardList().forEach(card -> ((PlayingCard)card).setRankIndex(rankIndex));
                } else {
                    System.out.println("Spectral Effect OUIJA not Possible");
                }
                model.getRunState().decreaseHandSizeBy(1);
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return !model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility();
        }
    },

    ECTOPLASM {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect ECTOPLASM");
            if (canPlay(model)) {
                model.getJokerManager().getCardList().get(model.getRand().nextInt(model.getJokerManager().getSize())).setEdition(model.getAllEditionList().get(4));
                model.getRunState().decreaseHandSizeBy(1 + model.getRunState().getUsedEctoplasm());
                model.getRunState().incrementUsedEctoplasm();
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().getSize() > 0;
        }
    },

    IMMOLATE {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect IMMOLATE");
            if (canPlay(model)) {
                List<? extends Card> cardList = model.isBoosterOpeningVisibility() ? model.getBoosterDrawModel().getPlayingCardsDrawn().stream().toList() : !model.getHoldingHandViewManager().getViewMap().isEmpty() ? model.getHoldingHandViewManager().getCardList() : null;
                if(cardList != null) {
                    Set<Card> cardSet = new HashSet<>();
                    while(cardSet.size() < cardList.size() && cardSet.size() < 5) {
                        cardSet.add(cardList.get(model.getRand().nextInt(cardList.size())));
                    }
                    cardSet.forEach(card -> {
                        if(model.isBoosterOpeningVisibility()) {
                            model.getBoosterDrawModel().getPlayingCardsDrawn().remove(card);
                        } else model.getHoldingHandViewManager().getCardList().remove(card);
                    });
                } else {
                    System.out.println("Spectral Effect IMMOLATE not Possible");
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return (!model.getHoldingHandViewManager().getViewMap().isEmpty() || model.isBoosterOpeningVisibility()) && model.getRunState().getMaxHandSize() > 1;
        }
    },

    ANKH {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect ANKH");
            if(canPlay(model)) {
                Card joker = model.getJokerManager().getCardList().get(model.getRand().nextInt(model.getJokerManager().getSize()));
                List<Card> cardList = model.getJokerManager().getCardList().stream().filter(card -> !joker.equals(card) ).toList();
                if(joker.getEdition().getEditionName().equals("Negative")) {
                    joker.setEdition(new Edition());
                }
                model.getJokerManager().create(joker);
                cardList.forEach(card -> {
                    model.getJokerManager().remove(card);
                });
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().getSize() > 1;
        }
    },

    DEJA_VU {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect DEJA_VU");
            if(canPlay(model)) {
                model.getSelectedCards().getFirst().setSeal(model.getAllSealList().get(1));
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },

    HEX {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect HEX");
            if(canPlay(model)) {
                Card joker = model.getJokerManager().getCardList().get(model.getRand().nextInt(model.getJokerManager().getSize()));
                joker.setEdition(model.getAllEditionList().get(3));
                model.getJokerManager().getCardList().forEach((card) -> {
                    if(card != joker)
                        model.getJokerManager().remove(card);
                });
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().getCardList().stream().anyMatch(card -> card.getEdition().getId() == 0);
        }
    },

    TRANCE {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect TRANCE");
            if(canPlay(model)) {
                model.getSelectedCards().getFirst().setSeal(model.getAllSealList().get(2));
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },

    MEDIUM {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect MEDIUM");
            if(canPlay(model)) {
                model.getSelectedCards().getFirst().setSeal(model.getAllSealList().get(3));

            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },

    CRYPTID {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect CRYPTID");
            if(canPlay(model)) {
                for(int  i = 0; i < 2; i++) {
                    model.getRunState().getPlayingDeck().addCard(model.getSelectedCards().getFirst());
                    if(model.isBoosterOpeningVisibility()) {
                        model.getBoosterDrawModel().getPlayingCardsDrawn().add(model.getSelectedCards().getFirst());
                    } else {
                        model.getHoldingHandViewManager().create((model.getSelectedCards().getFirst()));
                    }
                }
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getSelectedCards().size() == 1;
        }
    },

    THE_SOUL {
        @Override
        public void apply(GameModel model) {
            System.out.println("Spectral Effect THE_SOUL");
            if(canPlay(model)) {
                List<Integer> legendaryJoker = model.getAllJokerList().stream().filter(j -> "Legendary".equals(j.getRarity())).toList();
                model.getJokerManager().create(legendaryJoker.get(model.getRand().nextInt(legendaryJoker.size())));
            }
        }

        @Override
        public boolean canPlay(GameModel model) {
            return model.getJokerManager().getSize() < model.getRunState().getMaxJokers();
        }
    },

    BLACK_HOLE {
        @Override
        public void apply(GameModel model) {
            model.getPokerHandList().forEach(PokerHand::addLevel);
        }

        @Override
        public boolean canPlay(GameModel model) {
            return true;
        }
    };

    public void apply(GameModel model) {}

    public boolean canPlay(GameModel model) {
        return false;
    }

}
