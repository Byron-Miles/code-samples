#Makefile for encode and decode
ENCODE = PPM.o Transform.o Quantise.o HuffmanTree.o Huffman.o UNEIF.o Encode.o
DECODE = PPM.o Transform.o Quantise.o HuffmanTree.o Huffman.o UNEIF.o Decode.o

all: encode decode

encode: $(ENCODE)
	g++ $(ENCODE) -o encode

decode: $(DECODE)
	g++ $(DECODE) -o decode

clean:
	rm *.o encode decode

