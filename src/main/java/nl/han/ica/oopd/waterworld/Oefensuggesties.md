# Oefensuggesties
Hieronder een aantal oefensuggesties. De docent kan deze ook opgeven/verdelen als oefening om de functionaliteiten en werking van OOPG te leren kennen.

Oefening 5 is al een redelijke samenhangende set van functionaliteiten zodat Waterworld ook een spelletje is, in plaats van enkel een . In de eindopdracht moet je dus ook wel een dergelijk 'spel-achtig' set van functionaliteiten hebben. Deze mag je zelf verzinnen, de eisen zijn vooral technisch, toepassen van subclassesm, polymorfie, etc. zie onderwijsonline.

## Basis set
- [ ] 1. Zorg dat de bellen zodra ze door de speler worden aangeraakt roze worden.
- [ ] 2. Zorg dat er twee zwaardvissen gaan rondzwemmen (de tweede met een andere snelheid dan de eerste).
- [ ] 3. Introduceer een tweede tiletype (let op: die moet even groot zijn als de eerste!) waar bellen
  niet doorheen kunnen: als ze daar tegenaan komen, blijven ze dus hangen.
- [ ] 4. Zorg dat de player naar een willekeurige plek in de wereld teleporteert als-ie een zwaardvis aanraakt.
- [ ] 5. Maak er een spelletje van zodat de speler bellen kan vangen:
   - I. Voor elke bel krijgt hij punten;
   - II. Voor elke keer dat hij een zwaardvis raakt, raakt hij punten kwijt;
   - III. Uiteraard moet ook de score in beeld zijn (in plaats van het aantal geknapte bellen)
   - IV. En wordt de hiscore opgeslagen (in plaats van het aantal geklapte bellen).

# Verdere oefensuggesties
Dit is een set van verdere oefeningen. Enkele hulp media bestanden die je soms nodig hebt vind je al toegevoegd in de `resources` folder.
- [ ] A. Maak een nieuw tiletype ‘WeedTile’, waar de vis 2x zo langzaam zwemt als normaal (vis kan er van alle kanten doorheen)
- [ ] B. Maak een nieuw tiletype ‘HyperSpeedTile’, wat in principe een muntje is, die de vis kan oppakken waarna zijn snelheid verder verdubbelt is (deze til moet je dus van alle kanten kunnen oppakken).
- [ ] C. Maak een teller in het scherm die aftelt (gebruik een alarm), als de tijd op is verdwijnt de vis en toont Dashboard “Game Over!” (gebruik een Alarm uit OOPG, die elke seconde een trigger stuurt (kijk naar code van BubbleSpawner hoe je herhaalt Alarm (IAlarmListerner) kunt af laten gaan, door bij aanroep alarm weer nieuwe te aan te maken 😊)
- [ ] D. Zorg dat na een minuut een versnelde versie van de muziek afspeelt (Je kunt de ‘waterworld.mp3’ converteren via bv. online versnellingstool of Audicity)
- [ ] E. Gebruik een image editor om naast een ‘links’ en ‘rechts’ variant van de vis ook een 'naar beneden' en 'omhoog' variant te maken, en zorg dat de vis ook die kant uitkijkt als de gebruiker hem daar heen stuurt.
- [ ] F. Zorg dat je de vis naast met de pijltjestoetsen ook met de ‘W`, ‘D’, ‘S’ en ‘A’ toetsen kunt bedienen (top, right, bottom, left)
- [ ] G. Zorg dat de vis projectielen kan schieten met de spatiebalk, deze gaan naar links of naar rechts (afhankelijk of vis naar links of rechts respectievelijk kijkt)
- [ ] H. Zorg dat de zwaardvis tot stilstand komt als de vis ertegen aan botst
- [ ] I. Zorg dat je met de projectielen de zwaardvis kunt neerschieten
- [ ] J. Zorg dat de zwaardvis naar de bodem van het scherm stort als deze dood gaat (maak hiervoor ook opgave I, of als je die niet doet, laat de zwaardvis dan simpelweg doodgaan als je op spatie drukt zonder projectielen)
- [ ] K. Zorg dat de zwaardvis ook de tiles opeet als hij er doorheen zwemt (zet de zwaardvis bij begin van het spel op de rij met de tiles om te testen)
