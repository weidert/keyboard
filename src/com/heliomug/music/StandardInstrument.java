package com.heliomug.music;

public enum StandardInstrument {
    PIANO_GRAND("Acoustic Grand Piano", "Grand Piano", 0),
    PIANO_ACOUSTIC("Bright Acoustic Piano", "Bright Piano", 1),
    PIANO_ELECTRIC_GRAND("Electric Grand Piano", "Electric Piano", 2),
    PIANO_HONKY_TONK("Honky-tonk Piano", 3),
    PIANO_ELECTRIC_1("Electric Piano 1", 4),
    PIANO_ELECTRIC_2("Electric Piano 2", 5),
    HARPSICHORD("Harpsichord", 6),
    CLAVI("Clavi", 7),
    CELESTA("Celesta", 8),
    GLOCKENSPIEL("Glockenspiel", 9),
    MUSIC_BOX("Music Box", 10),
    VIBRAPHONE("Vibraphone", 11),
    MARIMBA("Marimba", 12),
    XYLOPHONE("Xylophone", 13),
    TUBULAR_BELLS("Tubular Bells", 14),
    DULCIMER("Dulcimer", 15),
    ORGAN_DRAWBAR("Drawbar Organ", 16),
    ORGAN_PERCUSSIVE("Percussive Organ", 17),
    ORGAN_ROCK("Rock Organ", 18),
    ORGAN_CHURCH("Church Organ", 19),
    ORGAN_REED("Reed Organ", 20),
    ACCORDIAN("Accordion", 21),
    HARMONICA("Harmonica", 22),
    ACCORDIAN_TANGE("Tango Accordion", 23),
    GUITAR_NYLON("Acoustic Guitar (nylon)", "Nylon Guitar", 24),
    GUITAR_STEEL("Acoustic Guitar (steel)", "Steel Guitar", 25),
    GUITAR_ELECTRIC_JAZZ("Electric Guitar (jazz)", "Jazz Guitar", 26),
    GUITAR_ELECTRIC_CLEAN("Electric Guitar (clean)", "Clean Guitar", 27),
    GUITAR_ELECTRIC_MUTED("Electric Guitar (muted)", "Muted Guitar", 28),
    GUITAR_OVERDRIVE("Overdriven Guitar", 29),
    GUITAR_DISTORTION("Distortion Guitar", 30),
    GUITAR_HARMONICS("Guitar harmonics", 31),
    BASS_ACOUSTIC("Acoustic Bass", 32),
    BASS_ELECTRIC("Electric Bass (finger)", 33),
    BASS_PICK("Electric Bass (pick)", 34),
    BASS_FRETLESS("Fretless Bass", 35),
    BASS_SLAP_1("Slap Bass 1", 36),
    BASS_SLAP_2("Slap Bass 2", 37),
    BASS_SYNTH_1("Synth Bass 1", 38),
    BASS_SYNTH_2("Synth Bass 2", 39),
    VIOLIN("Violin", 40),
    VIOLA("Viola", 41),
    CELLO("Cello", 42),
    CONTRABASS("Contrabass", 43),
    TREMOLO("Tremolo Strings", 44),
    PIZZICATO("Pizzicato Strings", 45),
    HARP_ORCHESTRAL("Orchestral Harp", 46),
    TIMPANI("Timpani", 47),
    STRING_ENSEMBLE_1("String Ensemble 1", 48),
    STRING_EMSEMBLE_2("String Ensemble 2", 49),
    STRING_SYNTH_1("SynthStrings 1", 50),
    STRING_SYNTH_2("SynthStrings 2", 51),
    AAHS("Choir Aahs", 52),
    OOHS("Voice Oohs", 53),
    VOICE_SYNTH("Synth Voice", 54),
    ORCHESTRA_HIT("Orchestra Hit", 55),
    TRUMPET("Trumpet", 56),
    TROMBONE("Trombone", 57),
    TUBA("Tuba", 58),
    TRUMPTE_MUTED("Muted Trumpet", 59),
    FRENCH_HORN("French Horn", 60),
    BRASS("Brass Section", 61),
    BRASS_SYNTH_1("SynthBrass 1", 62),
    BRASS_SYNTH_2("SynthBrass 2", 63),
    SAX_SOPRANO("Soprano Sax", 64),
    SAX_ALTO("Alto Sax", 65),
    SAX_TENOR("Tenor Sax", 66),
    SAX_BARITONE("Baritone Sax", 67),
    OBOE("Oboe", 68),
    ENGLISH_HORN("English Horn", 69),
    BASSOON("Bassoon", 70),
    CLARINET("Clarinet", 71),
    PICCOLO("Piccolo", 72),
    FLUTE("Flute", 73),
    RECORDER("Recorder", 74),
    PAN_FLUTE("Pan Flute", 75),
    BOTTLE("Blown Bottle", 76),
    SHAKUHACHI("Shakuhachi", 77),
    WHISTLE("Whistle", 78),
    OCARINA("Ocarina", 79),
    SQUARE("Lead 1 (square)", 80),
    SAWTOOTH("Lead 2 (sawtooth)", 81),
    CALLIOPE("Lead 3 (calliope)", 82),
    CHIFF("Lead 4 (chiff)", 83),
    CHARANG("Lead 5 (charang)", 84),
    VOICE("Lead 6 (voice)", 85),
    FIFTHS("Lead 7 (fifths)", 86),
    BASS_AND_LEAD("Lead 8 (bass + lead)", 87),
    NEW_AGE("Pad 1 (new age)", 88),
    WARM("Pad 2 (warm)", 89),
    POLYSYNTH("Pad 3 (polysynth)", 90),
    CHOIR("Pad 4 (choir)", 91),
    BOWED("Pad 5 (bowed)", 92),
    METALLIC("Pad 6 (metallic)", 93),
    HALOW("Pad 7 (halo)", 94),
    SWEEP("Pad 8 (sweep)", 95),
    RAIN("FX 1 (rain)", 96),
    SOUNTRACK("FX 2 (soundtrack)", 97),
    CRYSTAL("FX 3 (crystal)", 98),
    ATMOSPHERE("FX 4 (atmosphere)", 99),
    BRIGHTNESS("FX 5 (brightness)", 100),
    GOBLINS("FX 6 (goblins)", 101),
    ECHOES("FX 7 (echoes)", 102),
    SCI_FI("FX 8 (sci-fi)", 103),
    SITAR("Sitar", 104),
    BANJO("Banjo", 105),
    SHAMISEN("Shamisen", 106),
    KOTO("Koto", 107),
    KALIMBA("Kalimba", 108),
    BAG_PIPE("Bag pipe", 109),
    FIDDLE("Fiddle", 110),
    SHANAI("Shanai", 111),
    TINKLE_BELL("Tinkle Bell", 112),
    AGOGO("Agogo", 113),
    STEEL_DRUMS("Steel Drums", 114),
    WOODBLOCK("Woodblock", 115),
    TAIKO("Taiko Drum", 116),
    TOM("Melodic Tom", 117),
    DRUM_SYNTH("Synth Drum", 118),
    CYMBAL_REVERSE("Reverse Cymbal", 119),
    FRETS("Guitar Fret Noise", 120),
    BREATH("Breath Noise", 121),
    SEASHORE("Seashore", 122),
    BIRD("Bird Tweet", 123),
    TELEPHONE("Telephone Ring", 124),
    HELICOPTER("Helicopter", 125),
    APPLAUSE("Applause", 126),
    GUNSHOT("Gunshot", 127),
    DRUM_KIT("Drum Kit", 0);

    private String name;
    private String shortName;
    private int code;

    private StandardInstrument(String name, int code) {
    	this(name, name, code);
    }
    
    private StandardInstrument(String name, String shortName, int code) {
        this.name = name;
        this.shortName = shortName;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
    	return this.shortName;
    }
    
    public int getCode() {
        return this.code;
    }

    public String longString() {
        return String.format("Instrument: %s (%d)", name, code);
    }
    
    public String toString() {
    	return getName();
    }
}
