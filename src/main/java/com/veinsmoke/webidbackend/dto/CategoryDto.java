package com.veinsmoke.webidbackend.dto;

import net.minidev.json.annotate.JsonIgnore;

public record CategoryDto(@JsonIgnore Long id,
                          String name,
                          CategoryDto parent) {}
