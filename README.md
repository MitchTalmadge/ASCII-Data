# ASCIIGraph

[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/mitchtalmadge/asciigraph/master/LICENSE)
[![Build Status](https://travis-ci.org/MitchTalmadge/ASCIIGraph.svg?branch=master)](https://travis-ci.org/MitchTalmadge/ASCIIGraph)
[![GitHub issues](https://img.shields.io/github/issues/mitchtalmadge/asciigraph.svg)](https://github.com/mitchtalmadge/asciigraph/issues)

A small Java library for producing nice looking ASCII line ╭┈╯ graphs.

This library originated as a port of the JavaScript library [kroitor/asciichart](https://github.com/kroitor/asciichart).

![Example Graph](http://i.imgur.com/uiyYMfP.png) 

# Table of Contents

* [Installation](#installation)
* [Usage](#usage)
    * [Basic Example](#basic-example-sin-wave)
    * [Options](#options)
        * [Number of Rows](#number-of-rows)
        * [Tick Formatting](#tick-formatting)
* [Getting Help](#getting-help)
* [Contributing](#contributing)

## Installation

**Maven:**
```xml
<dependency>
    <groupId>com.mitchtalmadge</groupId>
    <artifactId>asciigraph</artifactId>
    <version>1.0.3</version>
</dependency>
```

**Gradle:**
```
compile 'com.mitchtalmadge:asciigraph:1.0.3'
```

## Usage

Graphs are simply large Strings. Use the `ASCIIGraph` class to configure the graph, 
and `plot()` to convert it to a String.

### Basic Example: Sin Wave
```java
public class Example {
    public static void main(String... args) {
        
        // The series should be an array of doubles.
        double[] sinWaveSeries = new double[120];
        
        // Calculate the Sin Wave.
        for (int i = 0; i < sinWaveSeries.length; i++)
            sinWaveSeries[i] = 15 * Math.sin(i * ((Math.PI * 4) / sinWaveSeries.length));
        
        // Plot and output the graph.
        System.out.println(ASCIIGraph.fromSeries(sinWaveSeries).withNumRows(15).plot());
    }
}
```
**Output:**
![Basic Example Graph](http://i.imgur.com/uiyYMfP.png) 

### Options

You can modify certain properties of the graph to change its appearance.

#### Number of Rows
Use the method `ASCIIGraph#withNumRows(int)` to change the number of rows on the graph.

`ASCIIGraph.fromSeries(data).plot()`

![Default Rows](http://i.imgur.com/DXiUYWR.png?1)                                    

`ASCIIGraph.fromSeries(data).withNumRows(8).plot()`

![8 Rows](http://i.imgur.com/JyO2ONo.png?1) 

`ASCIIGraph.fromSeries(data).withNumRows(3).plot()`

![3 Rows](http://i.imgur.com/eLIAkuv.png?1) 

#### Tick Formatting

You can format the tick marks (the numbers on the side of the axis) in two ways.

- You can how they are formatted, using a custom `DecimalFormat`.
- You can change their padding width. For example, a padding width of 8 will left-pad
the number `10.00` with 3 spaces, since it only takes up 5 characters.

```
ASCIIGraph.fromSeries(data).withTickFormat(new DecimalFormat("##0.000000")).withTickWidth(12).plot()
```
![Tick Formatted Graph](http://i.imgur.com/Ii081SS.png) 

## Getting Help

Please make an issue for help. Describe the issue as clearly as possible.

## Contributing

Want to help this library grow? I'd love your help. Please follow these guidelines when contributing:

- This repository closely follows the git-flow workflow. Please brush up on this, as PRs
which do not follow the workflow may be rejected. Not to mention, git-flow is 
popular and will benefit you in other projects as well. :)
- Please open an issue first, or comment on an existing issue. Don't just start coding without letting
me know and expect me to accept it.

### New Features
When working on a feature, please create your own branch **from the `develop` branch**, NOT `master`.
Prefix the branch name with `feature/`. Whatever comes after is up to you. Example: `feature/new-graph`.

### Bug Fixes
Simple bug fixes can be committed to the `develop` branch without the need for a new branch.

Hotfixes, which are meant to quickly patch a broken release, **should branch off of `master`**, 
NOT `develop`. Similarly to feature branches, prefix the branch name with `hotfix/`.
