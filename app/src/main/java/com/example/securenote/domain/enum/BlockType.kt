package com.example.securenote.domain.enum

enum class BlockType(val value: Int) {
    TEXT(0),
    IMAGE(1);

    companion object {
        fun getBlockType(value: Int): BlockType {
            return entries.firstOrNull { it.value == value } ?: TEXT
        }
    }
}