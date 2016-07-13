package com.zzkun.util.vjudge;

import com.zzkun.model.ContestStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/13.
 */
public class VJRankParserTest {

    VJRankParser parser = new VJRankParser();

    @Test
    public void parse() throws Exception {
        List<String> list = FileUtils.readLines(new File("temp/vjudge"), "utf8");
        ContestStatus contestStatus = parser.parse(list, new HashMap<>());
        System.out.println(contestStatus);
    }
}