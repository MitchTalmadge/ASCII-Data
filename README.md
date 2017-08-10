# ASCIIGraph

[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/mitchtalmadge/asciigraph/master/LICENSE)
[![Build Status](https://travis-ci.org/MitchTalmadge/ASCIIGraph.svg?branch=master)](https://travis-ci.org/MitchTalmadge/ASCIIGraph)
[![GitHub issues](https://img.shields.io/github/issues/mitchtalmadge/asciigraph.svg)](https://github.com/mitchtalmadge/asciigraph/issues)

A small Java library for producing nice looking ASCII line ╭┈╯ graphs.

This library originated as a port of the JavaScript library [kroitor/asciichart](https://github.com/kroitor/asciichart).

![Example Graph](http://i.imgur.com/uiyYMfP.png) 

## Installation

**Maven:**
```xml
<dependency>
    <groupId>com.mitchtalmadge</groupId>
    <artifactId>asciigraph</artifactId>
    <version>1.0.1</version>
</dependency>
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
```
   15.00 ┤           ╭──────╮                                                    ╭──────╮                                        
   12.86 ┤        ╭──╯      ╰──╮                                              ╭──╯      ╰──╮                                     
   10.71 ┤      ╭─╯            ╰─╮                                          ╭─╯            ╰─╮                                   
    8.57 ┤    ╭─╯                ╰─╮                                       ╭╯                ╰─╮                                 
    6.43 ┤   ╭╯                    ╰╮                                    ╭─╯                   ╰╮                                
    4.29 ┤  ╭╯                      ╰╮                                  ╭╯                      ╰╮                               
    2.14 ┤╭─╯                        ╰─╮                              ╭─╯                        ╰─╮                             
    0.00 ┼╯                            ╰╮                            ╭╯                            ╰╮                            
   -2.14 ┤                              ╰─╮                        ╭─╯                              ╰─╮                        ╭─
   -4.29 ┤                                ╰╮                      ╭╯                                  ╰╮                      ╭╯ 
   -6.43 ┤                                 ╰─╮                   ╭╯                                    ╰─╮                   ╭╯  
   -8.57 ┤                                   ╰╮                ╭─╯                                       ╰╮                ╭─╯   
  -10.71 ┤                                    ╰─╮            ╭─╯                                          ╰─╮            ╭─╯     
  -12.86 ┤                                      ╰──╮      ╭──╯                                              ╰──╮      ╭──╯       
  -15.00 ┤                                         ╰──────╯                                                    ╰──────╯          
```

### Options

You can modify certain properties of the graph to change its appearance.

#### Number of Rows
Use the method `ASCIIGraph#withNumRows(int)` to change the number of rows on the graph.

`ASCIIGraph.fromSeries(data).plot()`
```
   19.34 ┤                          ╭╮                 ╭
   18.32 ┤                         ╭╯│                ╭╯
   17.30 ┤                         │ │               ╭╯ 
   16.29 ┤                        ╭╯ │              ╭╯  
   15.27 ┤                        │  ╰╮  ╭╮       ╭╮│   
   14.25 ┤                    ╭╮ ╭╯   │ ╭╯│ ╭╮    │╰╯   
   13.23 ┤                   ╭╯│ │    │ │ ╰─╯│   ╭╯     
   12.21 ┤                 ╭╮│ ╰╮│    │ │    ╰╮  │      
   11.19 ┤                ╭╯││  ╰╯    │╭╯     ╰╮╭╯      
   10.17 ┤               ╭╯ ╰╯        ││       ╰╯       
    9.15 ┤            ╭╮ │            ││                
    8.13 ┤           ╭╯│ │            ╰╯                
    7.11 ┤          ╭╯ ╰─╯                              
    6.10 ┤       ╭─╮│                                   
    5.08 ┤       │ ││                                   
    4.06 ┤     ╭─╯ ╰╯                                   
    3.04 ┤    ╭╯                                        
    2.02 ┤  ╭─╯                                         
    1.00 ┼──╯    
```                                       

`ASCIIGraph.fromSeries(data).withNumRows(8).plot()`
```
   19.34 ┤                         ╭─╮                 ╭
   16.72 ┤                        ╭╯ │              ╭──╯
   14.10 ┤                   ╭─╮ ╭╯  ╰╮ ╭────╮   ╭──╯   
   11.48 ┤                ╭─╮│ ╰─╯    │╭╯    ╰───╯      
    8.86 ┤           ╭─╮ ╭╯ ╰╯        ╰╯                
    6.24 ┤       ╭─╮╭╯ ╰─╯                              
    3.62 ┤    ╭──╯ ╰╯                                   
    1.00 ┼────╯  
```                                       

`ASCIIGraph.fromSeries(data).withNumRows(3).plot()`
```
   19.34 ┤                        ╭───╮  ╭╮       ╭╮╭───
   10.17 ┤       ╭─╮╭─────────────╯   ╰──╯╰───────╯╰╯   
    1.00 ┼───────╯ ╰╯                                   
```

#### Tick Formatting

You can format the tick marks (the numbers on the side of the axis) in two ways.

- You can how they are formatted, using a custom `DecimalFormat`.
- You can change their padding width. For example, a padding width of 8 will left-pad
the number `10.00` with 3 spaces, since it only takes up 5 characters.

```
ASCIIGraph.fromSeries(data).withTickFormat(new DecimalFormat("##0.000000")).withTickWidth(12).plot()
```

```
   19.342350 ┤                         ╭─╮                 ╭
   16.722014 ┤                        ╭╯ │              ╭──╯
   14.101679 ┤                   ╭─╮ ╭╯  ╰╮ ╭────╮   ╭──╯   
   11.481343 ┤                ╭─╮│ ╰─╯    │╭╯    ╰───╯      
    8.861007 ┤           ╭─╮ ╭╯ ╰╯        ╰╯                
    6.240671 ┤       ╭─╮╭╯ ╰─╯                              
    3.620336 ┤    ╭──╯ ╰╯                                   
    1.000000 ┼────╯                                         
```

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