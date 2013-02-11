/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// package comp311.jmf.effect;

import javax.media.*;
import javax.media.format.*;
import javax.media.format.VideoFormat.*;


/**
 *
 * @author ashoka
 */

public class GreyEffect32 implements javax.media.Effect
{
    private static String EffectName="GreyEffect32";
    /** chosen input Format **/
    protected Format inputFormat;
    /** chosen output Format **/
    protected Format outputFormat;
    /** supported input Formats **/
    protected Format[] supportedInputFormats=new Format[1];
    /** supported output Formats **/
    protected Format[] supportedOutputFormats=new Format[1];


    // RGB bit masks
    static final private int rMask = 0x000000ff;
    static final private int gMask = 0x0000ff00;
    static final private int bMask = 0x00ff0000;
    
    /** initialize the formats **/
    public GreyEffect32() 
    {
      supportedInputFormats = new Format[] {
          new RGBFormat(null,
                          Format.NOT_SPECIFIED,
                          Format.intArray,
                          Format.NOT_SPECIFIED,
                          32,
                          rMask, gMask, bMask,
                          1, Format.NOT_SPECIFIED,
                          Format.FALSE,
                          Format.NOT_SPECIFIED)
            };

        supportedOutputFormats = new Format[] {
            new RGBFormat(null,
                          Format.NOT_SPECIFIED,
                          Format.intArray,
                          Format.NOT_SPECIFIED,
                          32,
                          rMask, gMask, bMask,
                          1, Format.NOT_SPECIFIED,
                          Format.FALSE,
                          Format.NOT_SPECIFIED) 
            };
    }

    /** get the resources needed by this effect **/
    public void open() throws ResourceUnavailableException {
    }

    /** free the resources allocated by this codec **/
    public void close() {
    }

    /** reset the codec **/
    public void reset() {
    }

    /** no controls for this simple effect **/
    public Object[] getControls() {
        return null;
    }
    
    /**
     * Return the control.
    **/
    public Object getControl(String controlType) {
        return null;
    }

    /************** format methods *************/
    /** set the input format **/
    public Format setInputFormat(Format input) {
        // the following code assumes valid Format
        inputFormat = input;
        return inputFormat;
    }

    /** set the output format **/
    public Format setOutputFormat(Format output) {
        // the following code assumes valid Format
        outputFormat = output;
        return outputFormat;
    }

    /** get the input format **/
    protected Format getInputFormat() {
        return inputFormat;
    }
    
    /** get the output format **/
    protected Format getOutputFormat() {
        return outputFormat;
    }

    /** supported input formats **/
    public Format [] getSupportedInputFormats() {
        return supportedInputFormats;
    }

    /** output Formats for the selected input format **/
    public Format [] getSupportedOutputFormats(Format in) {
        if (!(in instanceof RGBFormat) )
            return new Format[0];
        VideoFormat ivf=(VideoFormat) in;
        if (!ivf.matches(supportedInputFormats[0]))
            return new Format[0];
        RGBFormat ovf= new RGBFormat();
        return new Format[] {ovf};
    }

    /** return effect name **/
    public String getName() {
        return EffectName;
    }

    /** do the processing **/
    public int process(Buffer inputBuffer, Buffer outputBuffer)
    {
        // == prolog
        int[] inData = (int[])inputBuffer.getData();
        int inLength = inputBuffer.getLength();
        int inOffset = inputBuffer.getOffset();
        int outOffset = inOffset;
        
        int[] outData = validateByteArraySize(outputBuffer,inLength);
        
        // == main
        while (inOffset < inLength) {
            int pixel = (int) inData[inOffset++]; 
            int r = pixel & rMask;
            int g = (pixel & gMask)>>8;
            int b = (pixel & bMask)>>16;
            double grey =  (0.212671*r + 0.715160*g + 0.072169*b);
            int outpixel = (int) grey;
            outpixel = (outpixel << 8) | (int) grey;
            outpixel = (outpixel << 8) | (int) grey;
            outData[outOffset++] = outpixel;
        }
        
        // == epilog
        outputBuffer.setHeader(inputBuffer.getHeader());
        outputBuffer.setLength(inLength);
        outputBuffer.setFormat(inputBuffer.getFormat());
        outputBuffer.setFlags(inputBuffer.getFlags());
        outputBuffer.setSequenceNumber(inputBuffer.getSequenceNumber());
        outputBuffer.setDuration(inputBuffer.getDuration());
        outputBuffer.setTimeStamp(inputBuffer.getTimeStamp());
   
        return BUFFER_PROCESSED_OK;
    }

    /**
    * Utility: validate that the Buffer object's data size is at least
    * newSize bytes.
    * @return array with sufficient capacity
    **/
    protected int[] validateByteArraySize(Buffer buffer,int newSize) {
        Object objectArray=buffer.getData();
        int[] typedArray;
        if (objectArray instanceof int[]) { // is correct type AND not null
            typedArray=(int[])objectArray;
            if (typedArray.length >= newSize ) // is sufficient capacity
                return typedArray;
        }
        typedArray = new int[newSize];
        buffer.setData(typedArray);
        return typedArray;
    }
}

