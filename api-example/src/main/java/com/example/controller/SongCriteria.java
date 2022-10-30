package com.example.controller;

import jakarta.validation.constraints.Min;

public record SongCriteria(@Min(1) Integer singer) {
}
