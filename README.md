# Music Visualiser Project

Name:Richard Dennis

Student Number: C18415664

# Description of the assignment
ITS A BAD [OSU](https://osu.ppy.sh/home) CLONE!
(note i have never played osu i was gonna make a dance of the technomancer clone but im not that confident and this seemed more doable)
well kinda it may be monochrome and lacking any net functionallity and not have as many level elments and not have a proper difuclty curver or as nice a 

# Instructions
boot times can be a little slow beacuse it has to load every song i tried loading a song when a menu item was selected and it woulnt load documation says it has to be done in setup so i did it there.
the two optsion lead to simlar looking menus clicking on a cover leads to the the game
-if create was selected then clicking will place a white cirlce and when the song ends it saves it to the songs csv file
-if play was selected then it reads of the csv of the song and places clickables in time with the music

# How it works
each song is an object and has a csv a cover and a audio file acoicated with it
also i built a poor facily of a state machine as part of the menu system but idk how well i did



# What I am most proud of in the assignment
- adding a better sound libary and then Wrangling the .sh files without any documentation
- dealing with some increadly unhelpful bug reports
- writing and reading cvs files

# What I am Least proud of in the assignment
- my code base in a attemt to be readable as well as optimized i manged to do neither its a disater time just look at that boot time its a disaster area and theres so much stuff that should be made into methods thats just multiple times
- the graphics

# Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)