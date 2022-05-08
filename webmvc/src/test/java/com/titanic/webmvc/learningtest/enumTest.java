package com.titanic.webmvc.learningtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class enumTest {

    @DisplayName("enum에서 value에 해당하는 상수 찾기")
    @Test
    void find() {
        assertThat(Rank.find(5, true).get().getPrize()).isEqualTo(30_000_000);
    }

    @Test
    void plus() {
        Rank.SECOND.plusWinners();
        assertThat(Rank.SECOND.getWinners()).isEqualTo(1);
    }
}

enum Rank {
    FIRST(6, 2_000_000_000, 0),
    SECOND(5, 30_000_000, 0),
    THIRD(5, 1_500_000, 0);

    private final int countOfMatch;
    private final long prize;
    private int winners;

    Rank(int countOfMatch, long prize, int winners) {
        this.countOfMatch = countOfMatch;
        this.prize = prize;
        this.winners = winners;
    }

    public static Optional<Rank> find(int countOfMatch, boolean isBounsWinner) {
        for (Rank rank : values()) {
            if (isBounsWinner) {
                return Optional.of(Rank.SECOND);
            }
            if (rank.getCountOfMatch() == countOfMatch) {
                return Optional.of(rank);
            }
        }
        return Optional.empty();
    }

    void plusWinners() {
        winners++;
    }

    public int getCountOfMatch() {
        return countOfMatch;
    }

    public long getPrize() {
        return prize;
    }

    public int getWinners() {
        return winners;
    }
}
