using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Collections;

namespace MetodaSasiadowK
{
    class Program
    {
        private static List<Iris> setOfIrises = new List<Iris>();
        private static List<Iris> nearestNeighbours = new List<Iris>();
        private static Dictionary<Iris, double> distances = new Dictionary<Iris, double>();
        private static Dictionary<string, int> amoutOfTypes = new Dictionary<string, int>();
        private static int parameterK = 0;
        private static int setosa = 0;
        private static int versicolor = 0;
        private static int virginica = 0;

        static void Main(string[] args)
        {
            Console.WriteLine("Metoda K Najbliższych Sąsiadów");
            Console.WriteLine();

            //Wczytanie pliku
            readFile(setOfIrises, "IrisData.txt");

            //Wprowadzenie współrzędnych nowego punktu
            //W przypadku liczby z ułamkiem dziesiętym należy użyć przecinka zamiast kropki
            Console.WriteLine("Wprowadź dane obiektu");
            List<double> inputCoordinates = new List<double>();
            setNewCoordinates(inputCoordinates);
            Iris inputIris = new Iris(inputCoordinates[0], inputCoordinates[1], inputCoordinates[2], inputCoordinates[3]);

            //Wprowadzenie liczby sąsiadów K
            Console.Write("Parametr K: ");
            parameterK = Convert.ToInt32(Console.ReadLine());

            //Stworzenie kolekcji Dictionary<Iris,double> zawierającej obiekty wraz z odległościami od wprowadzonego obiektu 
            foreach (Iris singleIris in setOfIrises)
            {
                distances.Add(singleIris, getDistanceBeetwen(inputIris, singleIris));
            }

            //Wyszukanie najbliższych K sąsiadów
            findNeighbours();

            //Zliczenie typów
            countTypes();

            //Prezentacja wyników
            Console.WriteLine();
            Console.WriteLine("Neighbours:");
            foreach (Iris singleNeighbour in nearestNeighbours)
            {
                singleNeighbour.showIris();
            }
            Console.WriteLine();
            Console.WriteLine("Setosa: " + setosa + "  Versicolor: " + versicolor + "  Virginica: " + virginica);
            Console.WriteLine();
            Console.WriteLine("Obiekt o parametrach:");
            Console.WriteLine("leaf-length = " + inputIris.getLeafLength());
            Console.WriteLine("leaf-width = " + inputIris.getLeafWidth());
            Console.WriteLine("petal-length = " + inputIris.getPetalLength());
            Console.WriteLine("petal-width = " + inputIris.getPetalWidth());
            Console.WriteLine("dla parametru K = " +parameterK);
            Console.WriteLine("jest klasy " + getProperType(setosa, versicolor, virginica));
            Console.WriteLine();
            Console.WriteLine("Wcisnij dowolny klawisz aby zakonczyc dzialanie programu.");
            Console.ReadKey();
        }

        private static void setNewCoordinates(List<double> inputCoordinates)
        {
            try
            {
                Console.Write("LeafLength: ");
                inputCoordinates.Add(Convert.ToDouble(Console.ReadLine()));
                Console.Write("LeafWidth: ");
                inputCoordinates.Add(Convert.ToDouble(Console.ReadLine()));
                Console.Write("PetalLength: ");
                inputCoordinates.Add(Convert.ToDouble(Console.ReadLine()));
                Console.Write("PetalWidth: ");
                inputCoordinates.Add(Convert.ToDouble(Console.ReadLine()));
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                Console.WriteLine();
                Console.WriteLine("Tylko liczby są dozwolone! Wciśnij dowolny klawisz aby zakończyć działanie programu.");
                Console.ReadLine();
                Environment.Exit(-1);
            }
        }

        private static void readFile(List<Iris> setOfIrises, string filePath)
        {
            try
            {
                using (StreamReader sReader = new StreamReader(filePath))
                {
                    string line = sReader.ReadLine();
                    while (!sReader.EndOfStream)
                    {
                        line = sReader.ReadLine();
                        string[] parts;

                        parts = line.Split(default(string[]), StringSplitOptions.RemoveEmptyEntries);
                        Iris newIris = new Iris(Convert.ToDouble(parts[0]), Convert.ToDouble(parts[1]), Convert.ToDouble(parts[2]), Convert.ToDouble(parts[3]), parts[4]);
                        setOfIrises.Add(newIris);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("The file could not be read.");
                Console.WriteLine(e.Message);
            }
        }

        private static double getDistanceBeetwen(Iris inputIris, Iris anotherIris)
        {
            return Math.Sqrt(Math.Pow((inputIris.getLeafLength() - anotherIris.getLeafLength()),2) 
                + Math.Pow((inputIris.getLeafWidth() - anotherIris.getLeafWidth()), 2) 
                + Math.Pow((inputIris.getPetalLength() - anotherIris.getPetalLength()), 2) 
                + Math.Pow((inputIris.getPetalWidth() - anotherIris.getPetalWidth()), 2));
        }

        private static void findNeighbours()
        {
            double minLength = 0;
            double actualLength = 0;
            bool firstLoop = true;
            Iris neighbour = new Iris();
            for (int i = 0; i < parameterK; i++)
            {
                foreach (Iris singleIris in setOfIrises)
                {
                    actualLength = distances[singleIris];
                    if (minLength > actualLength || firstLoop == true)
                    {
                        minLength = actualLength;
                        neighbour = singleIris;
                        firstLoop = false;
                    }
                }
                firstLoop = true;
                nearestNeighbours.Add(neighbour);
                setOfIrises.Remove(neighbour);
            }
        }

        private static void countTypes()
        {
            foreach (Iris singleNeighbour in nearestNeighbours)
            {
                if (singleNeighbour.getType() == "Iris-setosa")
                {
                    setosa++;
                }
                else if (singleNeighbour.getType() == "Iris-versicolor")
                {
                    versicolor++;
                }
                else if (singleNeighbour.getType() == "Iris-virginica")
                {
                    virginica++;
                }
            }
        }

        private static string getProperType(int setosa, int versicolor, int virginica)
        {
            string newType = "none";
            if (setosa > versicolor)
            {
                if (setosa > virginica)
                {
                    newType = "Iris-setosa";
                }
                else if (setosa < virginica)
                {
                    newType = "Iris-virginica";
                }
                else if (setosa == virginica && setosa == versicolor)
                {
                    newType = "Iris-setosa lub Iris-virginica lub Iris-versicolor";
                }
                else if (setosa == virginica)
                {
                    newType = "Iris-setosa lub Iris-virginica";
                }
            }
            else if (versicolor > virginica)
            {
                newType = "Iris-versicolor";
            }
            else if (versicolor == virginica)
            {
                newType = "Iris-versicolor lub Iris-virginica";
            }
            else if (versicolor < virginica)
            {
                newType = "Iris-virginica";
            }
            return newType;
        }
    }
}
