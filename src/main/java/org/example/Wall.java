package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure{
    private List<Block> blocks ;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks
                .stream()
                .filter(block -> block.getColor().equals(color) ||
                        (block instanceof CompositeBlock &&
                                ((CompositeBlock) block)
                                        .getBlocks()
                                        .stream()
                                        .allMatch(compositeBlock->compositeBlock.getColor().equals(color))))
                .findAny();



    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .flatMap(block -> block instanceof CompositeBlock ?
                        ((CompositeBlock) block).getBlocks().stream() :
                        Stream.of(block))
                .filter(blocks -> blocks.getMaterial().equals(material))
                .collect(Collectors.toList());

    }

    @Override
    public int count() {
        return blocks.stream()
                .flatMap(
                block -> block instanceof CompositeBlock ?
                     Stream.concat(Stream.of(block),((CompositeBlock)block)
                             .getBlocks()
                             .stream()) : Stream.of(block)
                )
                .mapToInt(block -> 1)
                .sum();



    }
}


