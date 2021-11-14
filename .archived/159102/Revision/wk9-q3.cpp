#include <iostream>
#include <fstream>
#include <string>

#define thisPixel picture[x][y]

class pixel_class {
private:
    int red, green, blue;
    bool exclude;  // if true, do not check this pixel
public:
    void loaddata(int v1, int v2, int v3);
    void datatofile(std::fstream & ppmfile);
    int getR() { return red; }
    int getG() { return green; }
    int getB() { return blue; }
    void setexclude(bool ex) { exclude = ex; }
    bool getexclude() { return exclude; }
};

int screenx = 500, screeny = 500;
pixel_class picture[500][500];



int main() {
    for (int y = 150; y <= 350; ++y) {
        for (int x = 150; x <= 350; ++x) {
                thisPixel.loaddata(0, 0xFF, 0xFF);
            }
        }

    std::fstream outfile;
    std::string filename;
    std::cout << "What would you like to save the file as? ";
    getline(std::cin, filename);

    outfile.open(filename.c_str(), std::fstream::out);
    outfile << "P3\n# " << filename << std::endl << screenx << " " << screeny << std::endl << 256 << std::endl;
    for (int y = 0; y < screeny; ++y) {
        for (int x = 0; x < screenx; x++) {
            outfile << thisPixel.getR() << " " << thisPixel.getG() << " " << thisPixel.getB() << " ";
        }
        outfile << std::endl;
    }
    return 0;
}

//--------------- methods for the pixel_class ------------
void pixel_class::loaddata(int v1, int v2, int v3) {
    red = v1;
    green = v2;
    blue = v3;
}

void pixel_class::datatofile(std::fstream & ppmfile) {
    // write the data for one pixel to the ppm file
    ppmfile << red << " " << green;
    ppmfile << " " << blue << "  ";
}
