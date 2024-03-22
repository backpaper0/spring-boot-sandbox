package com.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiceController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Dice dice;

    public DiceController(Dice dice) {
        this.dice = dice;
    }

    @GetMapping("/rolldice")
    public List<Integer> rolldice(@RequestParam(required = false) String player,
            @RequestParam int rolls) {
        List<Integer> result = dice.rollTheDice(rolls);
        if (player != null) {
            logger.info("{} is rolling the dice: {}", player, result);
        } else {
            logger.info("Anonymous player is rolling the dice: {}", result);
        }
        return result;
    }
}
