
                                                            README

# Scilab-UsageTracking
Usage Tracking Module in SCILAB for GSoC 2017

Common web browser (firefox or chrome) track anonymously user activity to have a better knowledge of their users. This allow the developers to focus on the most used features and avoid wasting too much times on not used ones. This will also be beneficial to the end user because he/she will be able to track his/her work. The main idea is to give the end user and the SCILAB developer a statistical overview of the functions, macros and commands mostly used individually and globally.

== Algorithm ==

The previous algorithm was being formed on the idea that the Usage Tracking Module will be developed for the Scilab Console Window. Therefore , the idea was to use the JVM capabilities to connect to Google Analytics and push some probing counters to track usage of :

a) each component : scinotes, xcos, help, uicontrol, etc...

b) each modules : string, io, etc... (without performance penalty)

c) on each functions call (without performance penalty).

But, now it's changed to track statistics within SciNotes because that's where all the major work is done. Accordingly, SciNotes currently parse the whole file and identify functions through known commands, macros, variables tables. Accordingly,

a) setup preferences to add a "do not track" options within Scilab.

b) implement parser counters for commands and macros (using a HashMap<String, int>, rather than a HashSet<String>).

c) push the counters using Google Analytics API "Measurement Protocol" (or another provider).

Language/s being used : JAVA, JFlex 
