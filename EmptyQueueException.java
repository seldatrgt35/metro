package metrohomework;

public class EmptyQueueException extends RuntimeException
{
   public EmptyQueueException()
   {
      this(null);
   } // end default constructor
   
   public EmptyQueueException(String message)
   {
      super(message);
   } // end constructor
} // end EmptyQueueException
