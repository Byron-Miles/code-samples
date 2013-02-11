/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// package comp311.jmf.effect;

import javax.media.*;
import javax.media.format.*;
import javax.media.format.VideoFormat.*;
import java.awt.Dimension;

/**
 *
 * @author ashoka
 */

// Modified from GreyEffect2.java
// Modified by: Byron Miles
// Last Updated: 18/10/2011
//
// Changed to add a 'black bars' to the top, bottom, left
// or right of the video.
//


public class BorderEffect implements javax.media.Effect
{
    private static String EffectName="BorderEffect";
    /** chosen input Format **/
    protected Format inputFormat;
    /** chosen output Format **/
    protected Format outputFormat;
    /** supported input Formats **/
    protected Format[] supportedInputFormats=new Format[1];
    /** supported output Formats **/
    protected Format[] supportedOutputFormats=new Format[1];

    // Border widths
    private int m_top;
    private int m_bottom;
    private int m_left;
    private int m_right;

    private int m_border;

    // RGB bit masks
    static final private int rMask = 0x000000ff;
    static final private int gMask = 0x0000ff00;
    static final private int bMask = 0x00ff0000;
    
    /** initialize the formats **/
    public BorderEffect() 
    {
      m_top = 1;
      m_bottom = 1;
      m_left = 1;
      m_right = 1;

      m_border = 0;

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

    // Set the widths of the borders
    public void setBorderWidths(int top, int bottom, int left, int right)
    {
      m_top = top;
      m_bottom = bottom;
      m_left = left;
      m_right = right;
    }

    // Set border color
    public void setBorderColor(int red, int green, int blue)
    {
        m_border = blue;
        m_border = (m_border << 8) | green;
        m_border = (m_border << 8) | red;
    }

    //

    /** do the processing **/
    public int process(Buffer inputBuffer, Buffer outputBuffer)
    {
        // == prolog
        int[] inData = (int[])inputBuffer.getData();
        int inLength = inputBuffer.getLength();
        int inOffset = inputBuffer.getOffset();
        int outOffset = inOffset;
        Dimension inSize = ((VideoFormat)inputFormat).getSize();
        
         /*
          ------------------
          |      top       |
          ------------------
          |  |          |  |
          |L |          |R |
          |  |    In    |  |
          |  |          |  |
          ------------------
          |     bottom     |
          ------------------
         */

        int topPixels = m_top * (inSize.width + m_left + m_right);
        int bottomPixels = m_bottom * (inSize.width + m_left + m_right);
        int leftPixels = m_left * inSize.height;
        int rightPixels = m_right * inSize.height;
        int borderPixels = topPixels + bottomPixels + leftPixels + rightPixels;

        int[] outData = validateByteArraySize(outputBuffer,inLength + borderPixels);
       
        // == main
        // Add top border pixels
        for(int i = 0; i < topPixels; ++i)
        {
            outData[outOffset++] = m_border;
        }
        // Add left and right border pixels
        while(inOffset < inLength)
        {
            // Left
            for(int i = 0; i < m_left; ++i)
            {
               outData[outOffset++] = m_border;
               }

            // Input
            for(int i = 0; i < inSize.width; ++i)
            {
               outData[outOffset++] = (int) inData[inOffset++];
               }

            // Right
            for(int i = 0; i < m_right; ++i)
            {
               outData[outOffset++] = m_border;
               }
        }
        //Add bottom border pixels
        for(int i = 0; i < bottomPixels; ++i)
        {
            outData[outOffset++] = m_border;
        }

        // == epilog
        outputBuffer.setHeader(inputBuffer.getHeader());
        outputBuffer.setLength(inLength + borderPixels);
        //Modify format size
        RGBFormat iFormat = (RGBFormat)inputBuffer.getFormat();
        Dimension outSize = new Dimension(inSize.width + m_left + m_right,
                                        inSize.height + m_top + m_bottom);
        RGBFormat oFormat = new RGBFormat(outSize,
                                          iFormat.getMaxDataLength() + borderPixels, 
                                          iFormat.getDataType(), 
                                          iFormat.getFrameRate(),
                                          iFormat.getBitsPerPixel(),
                                          iFormat.getRedMask(),
                                          iFormat.getGreenMask(),
                                          iFormat.getBlueMask(),
                                          iFormat.getPixelStride(),
                                          iFormat.getLineStride() + m_left + m_right,
                                          iFormat.getFlipped(),
                                          iFormat.getEndian());

        outputBuffer.setFormat(oFormat);
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

