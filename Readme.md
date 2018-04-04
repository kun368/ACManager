# ACM集训队管理系统

[![Build Status](https://travis-ci.org/kun368/ACManager.svg?branch=master)](https://travis-ci.org/kun368/ACManager)
[![codebeat badge](https://codebeat.co/badges/49176421-bc21-4672-ad36-d7b275ad2afa)](https://codebeat.co/projects/github-com-kun368-acmanager-master)
[![Release Version](https://img.shields.io/badge/release-1.1.0-red.svg)](https://github.com/kun368/ACManager/releases)
[![Language](https://img.shields.io/badge/language-java-orange.svg)](https://github.com/kun368/ACManager)
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg)](https://github.com/kun368/ACManager)
[![GPL Licence](https://badges.frapsoft.com/os/gpl/gpl.svg?v=103)](https://opensource.org/licenses/GPL-3.0/)

## 项目简介

提供 ACM 集训队日常做题、比赛、题目分析、队员评价与选拔等功能，目前已上线稳定运行2年。

- 毕设题目：ACM智能集训管理系统设计与DevOps实践
- Wiki文档（同步毕设撰写中）：[https://github.com/kun368/ACManager/wiki](https://github.com/kun368/ACManager/wiki)
- Docker镜像（自动构建）：[https://hub.docker.com/r/kun368/acmanager/](https://hub.docker.com/r/kun368/acmanager/)

## 主要功能

- 队员 UVa、HDU、POJ 等 OnlineJudge AC 题目统计与分析
- 队员 Codeforces / BestCoder / TopCoder 的 Rating 统计
- 日常比赛训练结果导入和分析
- 完善的的集训、阶段、比赛类型管理
- 队员、队伍管理，量化分析评价
- 随机组队赛智能分队
- 各大 OnlineJudge 近期比赛汇总
- 队员毕业去向统计
- 提供 RESTful API

## 技术栈

- 前端：JSP、JQuery、Bootstrap、Vue.js
- 后端：Java8、Kotlin、Spring、SpringMVC
- 数据：MySql、Druid、Hibernate、Spring Data JPA
- 搜索：Lucene
- 服务器：Tomcat、Nginx
- 开发工具：Maven、IntelliJ IDEA、JRebel
- 算法：Agnes、TrueSkill、KMP、DFS
- 持续集成：Docker、Docker-Compose、Travis

## 线上网站

- [http://192.168.119.213:8080/ACManager/](http://192.168.119.213:8080/ACManager/)
- [http://cise.sdust.edu.cn/acmanager/](http://cise.sdust.edu.cn/acmanager/)
