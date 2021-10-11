#include <iostream>
#include <fstream>

#include <string>
#include <vector>

#include <cmath>
#include <cstdlib>

// I write these out a lot so having macros makes it easier to read.
#define thisPixel picture[x][y]
#define thisButton buttons[i]

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

struct button {
    int top, bottom, left, right, count;
    bool damaged;

    button(int top, int bottom, int left, int right, int count, bool damaged) {
        this->top = top;
        this->bottom = bottom;
        this->left = left;
        this->right = right;
        this->count = count;
        this->damaged = damaged;
    }
};

void loadButtons();
void scanImage();
void findArea(int x, int y, bool invert);
void findDamaged();
void drawBoxes();
void saveImage();

int screenx, screeny, maxcolours;   // you must use these
pixel_class picture[600][600];      // you must use thism

// This is hardcoded now but if this were to be expanded, you would set this value with a calibration function.
int maxDeviation = 5;

vector<button> buttons; // These are the globals from above just... not in an annoying way.

int main() {

    // Step 1 : read in the image from Buttons.ppm
    loadButtons();

    // Step 2 : identify buttons and draw boxes
    scanImage();
    drawBoxes();

    // Step 3 : output the final .ppm file
    saveImage();
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

void scanImage() {
    for (int y = 0; y < 600; ++y) {
        for (int x = 0; x < 600; ++x) {
            // Check if we should ignore this pixel.
            if (thisPixel.getexclude()) continue;

            // If we find a pixel, use it as a seed to find the rest.
            if (thisPixel.getR() > 128 || thisPixel.getG() > 128 || thisPixel.getB() > 128) {
                buttons.push_back(button(y, y, x, x, 1, 0));
                findArea(x, y, 0); // Enter recusrive find function.
            }
        }
    }

    findDamaged();
}

// Genereal purpose function to find the area of a cirle or gap.
void findArea(int x, int y, bool invert) {
    // Base Cases
    if (thisPixel.getexclude()) return;
    if (!invert) {
        // Base Case A
        if (thisPixel.getR() <= 128 && thisPixel.getG() <= 128 && thisPixel.getB() <= 128) return;

        if      (y < buttons.back().top)    buttons.back().top    = y;
        else if (y > buttons.back().bottom) buttons.back().bottom = y;
        if      (x < buttons.back().left)   buttons.back().left   = x;
        else if (x > buttons.back().right)  buttons.back().right  = x;
    }
    else {
        // Base Case B
        if (thisPixel.getR() > 128 || thisPixel.getG() > 128 || thisPixel.getB() > 128) return;
    }
    thisPixel.setexclude(true);

    findArea(x + 1, y    , invert);
    findArea(x - 1, y    , invert);
    findArea(x    , y + 1, invert);
    findArea(x    , y - 1, invert);
}

// First note that a key feature of a circle is having a consistent radius at every angle.
// Given a list of cordinates for a circle (maxX, maxY, minX minY), find a radius, the centre point,
// and then use these to determine whether the button is perfectly circular or missing bits by
// checking whether radius at angle n is still a pixel with an R/G/B value > 128.
// We do it this way because just checking total pixel count has greater chance for error.
void findDamaged() {
    // So uh... I wanted to do something dumb and try and be a smartass. It kinda worked..? I don't have time to make more images to check
    // but yeah. c:
    //
    // I'd really have liked to find a way to check the edge pixels of the circle against their distance from the centre to determine
    // whether they are not a circle and thus are damaged, but I couldn't figure out how.
    // So, I just drew a circle with a maxDeviation distance from the edge and checked each pixel on that circle. I'd really like feedback
    // on how I could have made this work the otherway.
    for (int i = 0; i < buttons.size(); ++i) {
        // Get our width and height.
        int width = (thisButton.right - thisButton.left);
        int height = (thisButton.bottom - thisButton.top);

        // We need the radius to be the max() - the maxDeviation.
        // That is... that is the radius around the circle at which we would consider any pixels < 128 to be over the maximum allowed
        // varience for a circle. It also accounts for if the difference of the height and width being greater than maxDeviation.
        // We use an outer radius to check if it's circular, then and inner radius to check whether there are 4 holes.
        int oRadius = max(width, height) / 2 - maxDeviation, iRadius = 15;
        thisButton.count = 0;
        for (int theta = 0, x, y; theta <= 360; theta += 10) {
            // Get (X, Y) for each radius pixel then check it's value.
            x = oRadius * cos(theta) + thisButton.left + width / 2;
            y = oRadius * sin(theta) + thisButton.top + height / 2;
            if (thisPixel.getR() < 128 && thisPixel.getG() < 128 && thisPixel.getB() < 128) {
                thisButton.damaged = true;
                break;
            }

            // Get (X, Y) for each inner radius pixel then check whether it's part of a hole.
            x = iRadius * cos(theta) + thisButton.left + width / 2;
            y = iRadius * sin(theta) + thisButton.top + height / 2;
            if (thisPixel.getR() <= 128 && thisPixel.getG() <= 128 && thisPixel.getB() <= 128 && !thisPixel.getexclude()) {
                findArea(x, y, 1);
                thisButton.count++;
            }
        }
        if (thisButton.count < 4) thisButton.damaged = true;
    }
}

void drawBoxes() {
    // Is this a pointer to circles[index] or is it copying the value of circles[index] into a seperate entity of circlePosition?
    // I wanted circle to just pe a pointer so it's easier to read later on and less copying to do.
    for (int i = 0; i < buttons.size(); ++i) {
        for (int y = thisButton.top; y <= thisButton.bottom; ++y) {
            // Draw our left side pixels.
            if(!thisButton.damaged) picture[thisButton.left][y].loaddata(0, 0xFF, 0);
            else picture[thisButton.left][y].loaddata(0xFF, 0, 0);

            // Draw our horizontal pixels
            if (y == thisButton.top || y == thisButton.bottom) {
                for (int x = thisButton.left; x <= thisButton.right; ++x) {
                    if(!thisButton.damaged) thisPixel.loaddata(0, 0xFF, 0);
                    else thisPixel.loaddata(0xFF, 0, 0);
                }
            }

            // Draw the right side pixels.
            if(!thisButton.damaged) picture[thisButton.right][y].loaddata(0, 0xFF, 0);
            else picture[thisButton.right][y].loaddata(0xFF, 0, 0);
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
