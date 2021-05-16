String inputs;
#define relay1 2 
#define relay2 3 
void setup()
{
Serial.begin(9600);
pinMode(relay1, OUTPUT); 
pinMode(relay2, OUTPUT); 
digitalWrite(relay1, LOW); 
digitalWrite(relay2, LOW); 
}
void loop()
{
while(Serial.available()) 
{
delay(10); 
char c = Serial.read();
inputs += c;
}
if (inputs.length() >0)
{
Serial.println(inputs);

if(inputs == "a")
{
digitalWrite(relay1, LOW);
}
else if(inputs == "A")
{
digitalWrite(relay1, HIGH);
}
else if(inputs == "b")
{
digitalWrite(relay2, LOW);
}
else if(inputs == "B")
{
digitalWrite(relay2, HIGH);
}

inputs="";

}}
