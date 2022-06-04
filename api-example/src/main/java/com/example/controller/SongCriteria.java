package com.example.controller;

import javax.validation.constraints.Min;

public record SongCriteria(@Min(1) Integer singer) {
}
