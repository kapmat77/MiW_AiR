using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MetodaSasiadowK
{
    class Iris
    {
        double leafLength;
        double leafWidth;
        double petalLength;
        double petalWidth;
        string type = "none";

        public Iris()
        {
            leafLength = 0;
            leafWidth = 0;
            petalLength = 0;
            petalWidth = 0;
        }

        public Iris(double lL, double lW, double pL, double pW, string t)
        {
            leafLength = lL;
            leafWidth = lW;
            petalLength = pL;
            petalWidth = pW;
            type = t;
        }

        public Iris(double lL, double lW, double pL, double pW)
        {
            leafLength = lL;
            leafWidth = lW;
            petalLength = pL;
            petalWidth = pW;
        }

        public void showIris()
        {
            Console.WriteLine(leafLength + " " + leafWidth + " " + petalLength + " " + petalWidth + " " +type);
        }

        public string getType()
        {
            return type;
        }

        public double getLeafLength()
        {
            return leafLength;
        }

        public double getLeafWidth()
        {
            return leafWidth;
        }

        public double getPetalLength()
        {
            return petalLength;
        }

        public double getPetalWidth()
        {
            return petalWidth;
        }
    }
}
