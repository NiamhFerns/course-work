#include <iostream>
#include <fstream>
#include <cstdlib>
#include <string>
#include <vector>
#include <cmath>

#define thisPixel picture[x][y]

using namespace std;


class pixel_class {
private:
    int red, green, blue;
    bool exclude;  // if true, do not check this pixel
public:
    void loaddata(int v1, int v2, int v3);
    void datatofile(fstream & ppmfile);
    int getR() { return red; }
    int getG() { return green; }
    int getB() { return blue; }
    void setexclude(bool ex) { exclude = ex; }
    bool getexclude() { return exclude; }
};

void loadButtons();
void scanImage();
void findArea(int x, int y, bool invert);
void findDamaged();
void drawBoxes();
void saveImage();

int maxDeviation = 5;  // MUST be global if used
int screenx, screeny, maxcolours;   // you must use these
pixel_class picture[600][600];      // you must use thism
// Index Key
// 0 - Xmax    1 - Xmin
// 2 - Ymax    3 - Ymin
// 4 - pixels  5 - broken
struct circlePosition {
    int top, bottom, left, right, count;
    bool damaged;

    circlePosition(int top, int bottom, int left, int right, int count, bool damaged) {
        this->top = top;
        this->bottom = bottom;
        this->left = left;
        this->right = right;
        this->count = count;
        this->damaged = damaged;
    }
};

vector<circlePosition> circles; // These are the globals from above just... not in an annoying way.

int main() {

    // Step 1 : read in the image from Buttons.ppm
    loadButtons();

    // Step 2 : identify buttons and draw boxes
    scanImage();
    drawBoxes();

    // Step 3 : output the final .ppm file
    saveImage();
}

void scanImage() {
    for (int y = 0; y < 600; ++y) {
        for (int x = 0; x < 600; ++x) {
            // Check if we should ignore this pixel.
            if (thisPixel.getexclude()) continue;
            if (thisPixel.getR() > 128 || thisPixel.getG() > 128 || thisPixel.getB() > 128) {
                circles.push_back(circlePosition(y, y, x, x, 1, 0));
                findArea(x, y, 0); // Enter recusrive find function.
            }
        }
    }

    findDamaged();
}

void findArea(int x, int y, bool invert) {
    // Base Cases
    if (thisPixel.getexclude()) return;
    if (!invert) {
        if (thisPixel.getR() <= 128 && thisPixel.getG() <= 128 && thisPixel.getB() <= 128) return;
        circles.back().count++; // Increase pixel count for nth circle.
        if      (y < circles.back().top)    circles.back().top    = y;
        else if (y > circles.back().bottom) circles.back().bottom = y;
        else if (x < circles.back().left)   circles.back().left   = x;
        else if (x > circles.back().right)  circles.back().right  = x;
    }
    else {
        if (thisPixel.getR() > 128 || thisPixel.getG() > 128 || thisPixel.getB() > 128) return;
    }
    thisPixel.setexclude(true);

    findArea(x + 1, y    , invert);
    findArea(x - 1, y    , invert);
    findArea(x    , y + 1, invert);
    findArea(x    , y - 1, invert);
}

void findDamaged() {
    // So uh... I wanted to do something dumb and try and be a smartass. It kinda worked..? I don't have time to make more images to check
    // but yeah. c:
    //
    // I'd really have liked to find a way to check the edge pixels of the circle against their distance from the centre to determine
    // whether they are not a circle and thus are damaged, but I couldn't figure out how.
    // So, I just drew a circle with a maxDeviation distance from the edge and checked each pixel on that circle. I'd really like feedback
    // on how I could have made this work the otherway.

    for (int i = 0; i < circles.size(); ++i) {
        int width = (circles[i].right - circles[i].left);
        int height = (circles[i].bottom - circles[i].top);
        int deviation = abs(width - height), radius;

        if (width > height) radius = height / 2 - maxDeviation;
        else radius = width / 2 - maxDeviation;

        for (int theta = 0, x, y; theta <= 360; theta += 10) {
            x = radius * cos(theta) + circles[i].left + width / 2;
            y = radius * sin(theta) + circles[i].top + height / 2;

            if (thisPixel.getR() <= 128 && thisPixel.getG() <= 128 && thisPixel.getB() <= 128) circles[i].damaged = true;
        }

        if (circles[i].damaged) continue;

         radius = 15;
         circles[i].count = 0;
         for (int theta = 0, x, y; theta <= 360; theta += 10) {
             x = radius * cos(theta) + circles[i].left + width / 2;
             y = radius * sin(theta) + circles[i].top + height / 2;

             if (thisPixel.getR() <= 128 && thisPixel.getG() <= 128 && thisPixel.getB() <= 128 && !thisPixel.getexclude()) {
                 findArea(x, y, 1);
                 circles[i].count++;
             }
         }
        if (circles[i].count < 4) circles[i].damaged = true;
    }
}

void drawBoxes() {
    for (int i = 0; i < circles.size(); ++i) {
        for (int y = circles[i].top; y <= circles[i].bottom; ++y) {
            if(!circles[i].damaged) picture[circles[i].left][y].loaddata(0, 0xFF, 0);
            else picture[circles[i].left][y].loaddata(0xFF, 0, 0);

            for (int x = circles[i].left; x <= circles[i].right; ++x) {
                if (y != circles[i].top && y != circles[i].bottom) continue; // This is disgusting, I'm so sorry...
                if(!circles[i].damaged) thisPixel.loaddata(0, 0xFF, 0);
                else thisPixel.loaddata(0xFF, 0, 0);
            }

            if(!circles[i].damaged) picture[circles[i].right][y].loaddata(0, 0xFF, 0);
            else picture[circles[i].right][y].loaddata(0xFF, 0, 0);
        }
    }
}

void saveImage() {
    fstream outfile;
    string filename;
    cout << "What would you like to save the file as? ";
    getline(cin, filename);

    outfile.open(filename.c_str(), fstream::out);
    outfile << "P3\n# " << filename << endl << screenx << " " << screeny << endl << maxcolours << endl;
    for (int y = 0; y < screeny; ++y) {
        for (int x = 0; x < screenx; x++) {
            outfile << thisPixel.getR() << " " << thisPixel.getG() << " " << thisPixel.getB() << " ";
        }
        outfile << endl;
    }
}

void loadButtons() {
    // load the picture from Buttons.ppm
    int x, y, R, G, B;
    fstream infile;
    string infilename, line;
    infilename = "Buttons.ppm";
    infile.open(infilename.c_str(), fstream::in);
    if (infile.is_open() == false) {
        cout << "ERROR: not able to open " << infilename << endl;
        exit(2);
    }
    getline(infile, line);  // this line is "P3"
    getline(infile, line);  // this line is "# filename"
    infile >> screenx >> screeny;  // this line is the size
    infile >> maxcolours;  // this line is 256
    for (y = 0; y < screeny; y++) {
        for (x = 0; x < screenx; x++) {
            infile >> R >> G >> B;
            picture[x][y].loaddata(R, G, B);
            picture[x][y].setexclude(false);
        }
    }
    infile.close();
}

//--------------- methods for the pixel_class ------------
void pixel_class::loaddata(int v1, int v2, int v3) {
    red = v1;
    green = v2;
    blue = v3;
}

void pixel_class::datatofile(fstream & ppmfile) {
    // write the data for one pixel to the ppm file
    ppmfile << red << " " << green;
    ppmfile << " " << blue << "  ";
}
