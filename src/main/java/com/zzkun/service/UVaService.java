package com.zzkun.service;

import com.zzkun.model.UHuntChapterTree;
import com.zzkun.util.uhunt.ChapterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kun on 2016/7/14.
 */
@Service
public class UVaService {

    @Autowired
    private ChapterManager chapterManager;

    public List<String> getBookName() {
        Map<UHuntChapterTree, List<Integer>> bookMap = chapterManager.getBookMap();
        return bookMap.keySet().stream().map(tree -> tree.name).collect(Collectors.toList());
    }

    public List<String> getChapterName() {
        Map<UHuntChapterTree, List<Integer>> chapterMap = chapterManager.getChapterMap();
        return chapterMap.keySet().stream().map(tree -> tree.name).collect(Collectors.toList());
    }
}
